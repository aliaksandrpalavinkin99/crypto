package com.xm.crypto.controller.converter;

import com.xm.crypto.entity.OperationType;
import com.xm.crypto.exception.NotSupportedException;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OperationTypeConverter implements Converter<String, OperationType> {

    @Override
    public OperationType convert(String source) {
        if (EnumUtils.isValidEnum(OperationType.class, source)) {
            return OperationType.valueOf(source);
        } else if (EnumUtils.isValidEnum(OperationType.class, source.toUpperCase())) {
            return OperationType.valueOf(source.toUpperCase());
        } else {
            throw new NotSupportedException("[" + source + "] this operation type isn't supported");
        }
    }
}
