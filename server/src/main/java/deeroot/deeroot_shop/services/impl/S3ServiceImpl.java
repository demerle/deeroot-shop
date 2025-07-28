package deeroot.deeroot_shop.services.impl;

import deeroot.deeroot_shop.services.S3Service;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    @Autowired
    private S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    @Override
    public void uploadFile(MultipartFile file) throws IOException {
        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(file.getOriginalFilename())
                        .build(),
                RequestBody.fromBytes(file.getBytes()));
    }

    @Override
    public String uploadImageFilePublic(String key, byte[] imageBytes) throws IOException {
        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .acl(ObjectCannedACL.PUBLIC_READ)
                        .build(),
                RequestBody.fromBytes(imageBytes));

        return String.format("https://%s.s3.%s.amazonaws.com/%s",
                bucketName, region, key);
    }

    @Override
    public byte[] downloadFile(String key) {
        try{
            ResponseBytes<GetObjectResponse> responseBytes = s3Client.getObjectAsBytes(GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build());
            return responseBytes.asByteArray();
        }
        catch (NoSuchKeyException e){
            return null;
        }

    }


    public String generatePreviewImage(MultipartFile pdfFile) {
        try {
            // Validate file
            if (pdfFile.isEmpty()) {
                throw new IllegalArgumentException("PDF file is empty");
            }


            // Load PDF document from MultipartFile
            PDDocument document = PDDocument.load(pdfFile.getInputStream());
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            // Render first page as BufferedImage (150 DPI for good quality)
            BufferedImage image = pdfRenderer.renderImageWithDPI(0, 150, ImageType.RGB);

            // Convert to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            byte[] imageBytes = baos.toByteArray();

            String fileName = pdfFile.getOriginalFilename();
            String previewKey = fileName.substring(0, fileName.length()-4) + "-preview.jpg";


            String previewUrl = uploadImageFilePublic(previewKey, imageBytes);

            // Clean up resources
            document.close();
            baos.close();

            return previewUrl;

        } catch (IOException e) {
            System.out.print("I/O Error in PreviewUpload: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (NullPointerException e) {
            System.out.print("Null Pointer Exception in generatePreviewImage in " +
                    "S3ServiceImpl. Likely due to .substring being called on an " +
                    "empty fileName: \n" + e.getMessage());
            throw new RuntimeException(e);
        }


    }
}
