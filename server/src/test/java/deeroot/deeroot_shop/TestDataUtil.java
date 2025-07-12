package deeroot.deeroot_shop;

import deeroot.deeroot_shop.domain.entities.MusicItem;
import deeroot.deeroot_shop.domain.entities.User;

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
                .s3FileName("wasd")
                .imageFileName("moonlight.png")
                .imageFileType("image/png")
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
                .s3FileName("wasd")
                .imageFileName("moonlight.png")
                .imageFileType("image/png")
                .build();
    }

    public static User createTestUserA(){
        return User.builder()
                .name("dennis")
                .password("root")
                .email("dumb1@gmail.com")
                .build();
    }
    public static User createTestUserB(){
        return User.builder()
                .name("dennis2")
                .password("rootington")
                .email("dumb2@gmail.com")
                .build();
    }
}
