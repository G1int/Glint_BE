package com.swyp.glint.image.application;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    String uploadProfileImageFile(String filename, MultipartFile multipartFile);

    String uploadAuthenticationImageFile(String filename, MultipartFile multipartFile);

    byte[] getProfileImageFile(String filename);

    void deleteProfileImageFile(String filename);

    String getFileUrl(String objectKey);
}
