package deeroot.deeroot_shop.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {

    void uploadFile(MultipartFile file) throws IOException;

    String uploadImageFilePublic(String key, byte[] imageBytes) throws IOException;

    byte[] downloadFile(String key);

    String generatePreviewImage(MultipartFile pdfFile);
}
