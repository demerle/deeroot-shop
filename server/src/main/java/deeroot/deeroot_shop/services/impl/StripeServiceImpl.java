package deeroot.deeroot_shop.services.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import deeroot.deeroot_shop.domain.dto.payment.ProductRequestDto;
import deeroot.deeroot_shop.domain.dto.payment.StripeResponseDto;
import deeroot.deeroot_shop.services.StripeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeServiceImpl implements StripeService {

    @Value("${stripe.secret-key}")
    private String secretKey;

    public StripeResponseDto checkoutProducts(ProductRequestDto productRequest){
        Stripe.apiKey = secretKey;

        SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(productRequest.getName())
                .build();

        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency(productRequest.getCurrency() == null ? "USD" : productRequest.getCurrency())
                .setUnitAmount(productRequest.getAmount())
                .setProductData(productData)
                .build();

        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(productRequest.getQuantity())
                .setPriceData(priceData)
                .build();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/success")
                .setCancelUrl("http://localhost:8080/cancel")
                .addLineItem(lineItem)
                .build();


        Session session = null;
        try {
            session = Session.create(params);
        } catch (StripeException e) {
            System.out.println("Exception in stripe session creation: " + e.getMessage());
            return null;
        }

        return StripeResponseDto.builder()
                .status("Success")
                .message("Payment session created")
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();

    }
}
