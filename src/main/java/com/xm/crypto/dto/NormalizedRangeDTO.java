package com.xm.crypto.dto;

import com.xm.crypto.entity.Crypto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class NormalizedRangeDTO {

    private Crypto crypto;
    private BigDecimal price;
}
