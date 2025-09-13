package ru.taf.services;

import io.minio.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.taf.exceptions.BucketCreateException;
import ru.taf.exceptions.FileUploadException;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioService implements S3Service {

    private final MinioClient minioClient;

    @Value("${minio.covers-bucket}")
    private String coversBucket;

    @Value("${minio.texts-bucket}")
    private String textsBucket;

    @PostConstruct
    public void init() {
        createBucket(coversBucket);
        createBucket(textsBucket);
    }

    @Override
    public String uploadText(MultipartFile file) {
        return uploadFile(file, textsBucket);
    }

    @Override
    public String uploadCover(MultipartFile file) {
        return uploadFile(file, coversBucket);
    }


    private String uploadFile(MultipartFile file, String bucketName) {
        log.info("File uploading. Bucket={}, fileName={}", bucketName, file.getOriginalFilename());
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            log.error("File upload exception. Bucket={}, fileName={}", bucketName, file.getOriginalFilename());
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
            log.info("File uploaded successfully. Bucket={}, fileKey={}", bucketName, fileName);
            return fileName;
        } catch (Exception ex) {
            log.error("File creation exception", ex);
            throw new FileUploadException(ex.getMessage());
        }
    }


    private void createBucket(String bucketName) {
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());

                String policy = """
                        {
                            "Version": "2012-10-17",
                            "Statement": [
                                {
                                    "Effect": "Allow",
                                    "Principal": "*",
                                    "Action": ["s3:GetObject"],
                                    "Resource": ["arn:aws:s3:::%s/*"]
                                }
                            ]
                        }
                        """.formatted(bucketName);

                minioClient.setBucketPolicy(SetBucketPolicyArgs.builder()
                        .bucket(bucketName)
                        .config(policy)
                        .build());
            }
        } catch (Exception ex) {
            log.error("Bucket creation exception", ex);
            throw new BucketCreateException("Failed to create bucket: " + ex.getMessage());
        }
    }

    private String generateFileName(MultipartFile file) {
        String extension = Objects.requireNonNull(file.getOriginalFilename())
                .substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        return UUID.randomUUID() + "." + extension;
    }
}
