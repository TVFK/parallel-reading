package ru.taf.services;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioService implements S3Service {

    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucketName;

    @Override
    public String getFile(String fileKey) {
        try {
            InputStream inputStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileKey)
                            .build()
            );
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("Filed to download file. FileKey={}", fileKey, e);
            throw new RuntimeException("Failed to download file: " + fileKey, e);
        }
    }
}