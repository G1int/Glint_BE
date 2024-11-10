package com.swyp.glint.core.common.util;

import com.swyp.glint.core.auth.application.social.SocialType;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class SocialTypeConverter implements Converter<String, SocialType> {
    @Override
    public SocialType convert(String socialType) {
        return SocialType.valueOf(socialType.toUpperCase());
    }
}
