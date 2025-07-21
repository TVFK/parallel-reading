package ru.taf.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface MinioService {

    String uploadFile(MultipartFile file);
}
