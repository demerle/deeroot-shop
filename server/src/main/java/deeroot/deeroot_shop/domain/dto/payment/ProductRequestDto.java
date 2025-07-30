package deeroot.deeroot_shop.domain.dto.payment;

import deeroot.deeroot_shop.domain.dto.MusicItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {
    private Long amount;
    private Long quantity;
    private String name;
    private String currency;
    private List<MusicItemDto> items;
}
