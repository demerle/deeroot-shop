package deeroot.deeroot_shop.controllers;

import deeroot.deeroot_shop.domain.dto.MusicItemDto;
import deeroot.deeroot_shop.domain.dto.payment.ProductRequestDto;
import deeroot.deeroot_shop.domain.dto.payment.StripeResponseDto;
import deeroot.deeroot_shop.domain.entities.MusicItem;
import deeroot.deeroot_shop.domain.entities.User;
import deeroot.deeroot_shop.mappers.MusicItemMapper;
import deeroot.deeroot_shop.services.MusicItemService;
import deeroot.deeroot_shop.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/api")
public class CartController {


    private final UserService userService;
    private final MusicItemService musicItemService;
    private final MusicItemMapper musicItemMapper;



    public CartController(UserService userService, MusicItemMapper musicItemMapper, MusicItemService musicItemService) {
        this.userService = userService;
        this.musicItemMapper = musicItemMapper;
        this.musicItemService = musicItemService;
    }


    @PostMapping(path = "/users/cart/{id}")
    public ResponseEntity<MusicItemDto> addToCart(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        User user = userService.findByEmail(userDetails.getUsername()).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        MusicItem item = musicItemService.find(id).orElse(null);
        if(item == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            user.getShoppingCart().add(item);
            userService.save(user);

            return ResponseEntity.ok(musicItemMapper.toMusicItemDto(item));
        }
    }

    @DeleteMapping(path = "/users/cart/{id}")
    public ResponseEntity<List<MusicItemDto>> removeFromCart(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        MusicItem item = musicItemService.find(id).orElse(null);
        if(item == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            user.getShoppingCart().remove(item);
            userService.save(user);
            return ResponseEntity.ok(user.getShoppingCart().stream().map(musicItemMapper::toMusicItemDto).toList());
        }
    }

    @DeleteMapping(path = "/users/cart")
    public ResponseEntity<Boolean> emptyCart(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        userService.emptyCart(user);
        return ResponseEntity.ok(true);
    }

    @GetMapping(path = "/users/cart")
    public ResponseEntity<List<MusicItemDto>> getCartItems(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Set<MusicItem> items = user.getShoppingCart();
        List<MusicItemDto> dtos = items.stream().map(musicItemMapper::toMusicItemDto).toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);

    }
}
