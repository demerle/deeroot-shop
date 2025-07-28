package deeroot.deeroot_shop.domain.dto;

import deeroot.deeroot_shop.domain.entities.MusicItem;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MusicItemDto{
        private Long id;
        private String title;
        private String description;
        private String composer;
        private Double price;
        private String fileName;
        private String fileType;
        private String s3PreviewUrl;
        private Integer numPages;
}
