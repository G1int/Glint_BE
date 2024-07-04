package com.swyp.glint.image.application.dto;

public record ImageResponse(
        String filename,
        String url
) {

    public static ImageResponse of(String filename, String url) {
        return new ImageResponse(filename, url);
    }
}
