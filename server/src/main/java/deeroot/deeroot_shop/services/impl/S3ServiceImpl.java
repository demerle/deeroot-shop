package deeroot.deeroot_shop.services.impl;

import deeroot.deeroot_shop.services.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    @Autowired
    private S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Override
    public void uploadFile(MultipartFile file) throws IOException {
        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(file.getOriginalFilename())
                        .build(),
                RequestBody.fromBytes(file.getBytes()));
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
}
