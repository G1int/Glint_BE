package com.swyp.glint.image.application;

import com.swyp.glint.image.application.dto.ImageResponse;
import com.swyp.glint.image.domain.ProfileImageFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService  {

    private final FileUploadService fileUploadService;


    public ImageResponse uploadProfileImageFile(MultipartFile imageFile) {
        ProfileImageFile profileImageFile = ProfileImageFile.of(imageFile);

        return ImageResponse.of(profileImageFile.getUploadFileName(),fileUploadService.uploadProfileImageFile(profileImageFile.getUploadFileName(), profileImageFile.getImageFile()));
    }

    public ImageResponse uploadAuthenticationImageFile(MultipartFile imageFile) {
        ProfileImageFile profileImageFile = ProfileImageFile.of(imageFile);

        return ImageResponse.of(profileImageFile.getUploadFileName(),fileUploadService.uploadAuthenticationImageFile(profileImageFile.getUploadFileName(), profileImageFile.getImageFile()));
    }



}
