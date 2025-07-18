package deeroot.deeroot_shop.controllers;

import deeroot.deeroot_shop.domain.dto.payment.ProductRequestDto;
import deeroot.deeroot_shop.domain.dto.payment.StripeResponseDto;
import deeroot.deeroot_shop.services.impl.StripeService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<StripeResponseDto> createStripePaymentSessionLink(@RequestBody ProductRequestDto productRequestDto) {
        StripeResponseDto stripeResponseDto = stripeService.checkoutProducts(productRequestDto);
        System.out.println(stripeResponseDto.toString());
        return ResponseEntity.ok(stripeResponseDto);
    }

}
