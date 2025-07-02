package deeroot.deeroot_shop;

import deeroot.deeroot_shop.domain.entities.MusicItem;

public final class TestDataUtil {

    private TestDataUtil() {
    }

    public static MusicItem createTestMusicItemA(){
        return MusicItem.builder()
                .title("Moonlight Sonata")
                .description("Movement 3")
                .composer("Beethoven")
                .price(10.00)
                .fileName("moonlight.pdf")
                .fileType("application/pdf")
                .imageFileName("moonlight.png")
                .imageFileType("image/png")
                .s3Key("key")
                .s3Url("url")
                .build();
    }

    public static MusicItem createTestMusicItemB(){
        return MusicItem.builder()
                .title("Moonlight Sonata")
                .description("Movement 1")
                .composer("Beethoven")
                .price(10.00)
                .fileName("moonlight.pdf")
                .fileType("application/pdf")
                .imageFileName("moonlight.png")
                .imageFileType("image/png")
                .s3Key("key")
                .s3Url("url")
                .build();
    }
}
