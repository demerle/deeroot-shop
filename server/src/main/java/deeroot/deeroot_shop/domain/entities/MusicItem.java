package deeroot.deeroot_shop.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "music_items")
public class MusicItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "composer")
    private String composer;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType; // pdf or midi

    @Column(name = "s3_preview_url")
    private String s3PreviewUrl;

    @Column(name = "num_pages")
    private Integer numPages;

    public String toString(){
        return "MusicItem : \n" + "ID: " + id + "\nTitle: " + title + "\nDescription: " + description + "\nComposer: " + composer + "\nPrice: " + price + "\nFileName: " + fileName + "\nFileType: " + fileType;
    }

}
