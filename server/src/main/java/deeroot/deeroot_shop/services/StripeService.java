package deeroot.deeroot_shop.services;

import deeroot.deeroot_shop.domain.dto.payment.ProductRequestDto;
import deeroot.deeroot_shop.domain.dto.payment.StripeResponseDto;

public interface StripeService {

    StripeResponseDto checkoutProducts(ProductRequestDto productRequest);

}
