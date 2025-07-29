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

        StripeResponseDto stripeResponseDto = stripeService.checkoutProducts(productRequestDto);
        System.out.println(stripeResponseDto.toString());
        return ResponseEntity.ok(stripeResponseDto);
    }

    @PostMapping(path="/checkout/verify")
    public ResponseEntity<Boolean> verifyStripeSessionID(@RequestParam("session_id") String sessionId, @AuthenticationPrincipal UserDetails userDetails, @RequestBody List<MusicItemDto> list) {
        System.out.println("Inside verifyStripeSessionID endpoint");
        if (userDetails == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try{
            Stripe.apiKey = secretKey;
            Session session = Session.retrieve(sessionId);

            if (session.getStatus().equals("complete") && session.getPaymentStatus().equals("paid")){
                userService.updateUsersOwnedItemsWithNewItems(userDetails, list);
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
