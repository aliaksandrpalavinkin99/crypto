package com.xm.crypto.controller.converter;

import com.xm.crypto.entity.OperationType;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OperationTypeConverter implements Converter<String, OperationType> {

    @Override
    public OperationType convert(String source) {
        if (EnumUtils.isValidEnum(OperationType.class, source)) {
            return OperationType.valueOf(source);
        } else {
            return OperationType.valueOf(source.toUpperCase());
        }
    }
}
