package ru.taf.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface S3Service {

    String getFile(String fileName);
}
