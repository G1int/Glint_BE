package com.swyp.glint.image.domain;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class ProfileImageFile {

    private final MultipartFile imageFile;
    private final String uploadFileName;


    private ProfileImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
        this.uploadFileName = getFileName(Objects.requireNonNull(imageFile.getOriginalFilename()));
    }

    public static ProfileImageFile of(MultipartFile imageFile) {
        return new ProfileImageFile(imageFile);
    }


    private String getFileName(String imageFilePath) {
        List<String> filenames = Arrays.stream(imageFilePath.split("\\.")).collect(Collectors.toList());
        long unixTime = Instant.now().getEpochSecond();

        return "profile" + "_" + unixTime + "." + getSuffix(filenames);
    }

    private String getSuffix(List<String> fileNames) {
        String suffix = fileNames.get(fileNames.size() - 1);
        if ("jpg".equals(suffix)) {
            return "jpg";
        } else if ("png".equals(suffix)) {
            return "png";
        }

        return "jpg";
    }


}
