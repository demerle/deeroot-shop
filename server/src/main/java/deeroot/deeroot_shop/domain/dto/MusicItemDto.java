package deeroot.deeroot_shop.domain.dto;

import deeroot.deeroot_shop.domain.entities.MusicItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicItemDto{
        private Long id;
        private String title;
        private String description;
        private String composer;
        private Double price;
        private String fileName;
        private String fileType;
        private String s3FileName;
        private String imageFileName;
        private String imageFileType;
}
