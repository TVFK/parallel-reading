package ru.taf.services;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {

    String uploadText(MultipartFile file);

    String uploadCover(MultipartFile file);
}
