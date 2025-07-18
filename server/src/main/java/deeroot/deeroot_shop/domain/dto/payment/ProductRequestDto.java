package deeroot.deeroot_shop.domain.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {
    private Long amount;
    private Long quantity;
    private String name;
    private String currency;
}
