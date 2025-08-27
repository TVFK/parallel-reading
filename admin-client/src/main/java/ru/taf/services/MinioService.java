package ru.taf.services;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.taf.exceptions.FileUploadException;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioService implements S3Service {

    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucketName;

    @PostConstruct
    public void init() {
        createBucket();
    }

    @Override
    public String uploadFile(MultipartFile file) {
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new FileUploadException("file must have name");
        }

        String fileName = generateFileName(file);

        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            return fileName;
        } catch (Exception ex) {
            throw new FileUploadException(ex.getMessage());
        }
    }


    private void createBucket() {
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception ex) {
            throw new FileUploadException("Failed to create bucket: " + ex.getMessage());
        }
    }

    private String generateFileName(MultipartFile file) {
        String extension = Objects.requireNonNull(file.getOriginalFilename())
                .substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        return UUID.randomUUID() + "." + extension;
    }
}
