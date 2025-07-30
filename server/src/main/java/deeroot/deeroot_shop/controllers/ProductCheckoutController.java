package deeroot.deeroot_shop.controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.service.ProductService;
import deeroot.deeroot_shop.domain.dto.MusicItemDto;
import deeroot.deeroot_shop.domain.dto.payment.ProductRequestDto;
import deeroot.deeroot_shop.domain.dto.payment.StripeResponseDto;
import deeroot.deeroot_shop.domain.entities.MusicItem;
import deeroot.deeroot_shop.domain.entities.User;
import deeroot.deeroot_shop.repositories.UserRepository;
import deeroot.deeroot_shop.services.MusicItemService;
import deeroot.deeroot_shop.services.StripeService;
import deeroot.deeroot_shop.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class ProductCheckoutController {


    private final StripeService stripeService;
    private final UserService userService;
    private final MusicItemService musicItemService;
    private final UserRepository userRepository;

    @Value("${stripe.secret-key}")
    private String secretKey;

    public ProductCheckoutController(StripeService stripeService, UserService userService, MusicItemService musicItemService, UserRepository userRepository) {
        this.stripeService = stripeService;
        this.userService = userService;
        this.musicItemService = musicItemService;
        this.userRepository = userRepository;
    }

    @PostMapping(path = "/checkout")
    public ResponseEntity<StripeResponseDto> createStripePaymentSessionLink(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ProductRequestDto productRequestDto) {

        if (productRequestDto.getAmount() == 0){

        }
        if (userDetails == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.findByEmail(userDetails.getUsername()).orElse(null);
        if (user == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        boolean zero = true;
        for (MusicItemDto item : productRequestDto.getItems()){
            user.getPurchasedItems().add(musicItemService.findByFileName(item.getFileName()));
            if (item.getPrice() > 0){
                zero = false;
            }
        }
        if (zero){
            for (MusicItemDto item : productRequestDto.getItems()){
                MusicItem mapped = musicItemService.findByFileName(item.getFileName());
                user.getOwnedMusicItems().add(mapped);
            }
            userService.emptyPurchasedItems(user);
            userService.save(user);
            return ResponseEntity.ok(null);
        }

        StripeResponseDto stripeResponseDto = stripeService.checkoutProducts(productRequestDto);
        userService.save(user);

        return ResponseEntity.ok(stripeResponseDto);


    }

    @GetMapping(path="/checkout/verify")
    public ResponseEntity<Boolean> verifyStripeSessionID(@RequestParam("session_id") String sessionId, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        User user = userService.findByEmail(userDetails.getUsername()).orElse(null);
        if (user == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try{
            Stripe.apiKey = secretKey;
            Session session = Session.retrieve(sessionId);

            if (session.getStatus().equals("complete") && session.getPaymentStatus().equals("paid")){
                userService.updateUsersOwnedItemsWithNewItems(userDetails, user.getPurchasedItems().stream().toList());
                userService.emptyPurchasedItems(user);
                userService.save(user);
                return ResponseEntity.ok(true);
            }
            else{
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        }
        catch (StripeException e){
            System.out.println("Stripe Exception in verifyStripeSessionID: " + e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e){
            System.out.println("Generic Exception in verifyStripeSessionID: " + e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



}
