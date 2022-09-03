package com.xm.crypto.controller.converter;

import com.xm.crypto.entity.Crypto;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CryptoConverter implements Converter<String, Crypto> {

    @Override
    public Crypto convert(String source) {
        if (EnumUtils.isValidEnum(Crypto.class, source)) {
            return Crypto.valueOf(source);
        } else {
            return Crypto.valueOf(source.toUpperCase());
        }
    }
}
