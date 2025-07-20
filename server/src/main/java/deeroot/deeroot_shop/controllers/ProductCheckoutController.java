package deeroot.deeroot_shop.controllers;

import deeroot.deeroot_shop.domain.dto.payment.ProductRequestDto;
import deeroot.deeroot_shop.domain.dto.payment.StripeResponseDto;
import deeroot.deeroot_shop.services.StripeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductCheckoutController {


    private final StripeService stripeService;

    public ProductCheckoutController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping(path = "/checkout")
    public ResponseEntity<StripeResponseDto> createStripePaymentSessionLink(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ProductRequestDto productRequestDto) {
        StripeResponseDto stripeResponseDto = stripeService.checkoutProducts(productRequestDto);
        System.out.println(stripeResponseDto.toString());
        return ResponseEntity.ok(stripeResponseDto);
    }



}
