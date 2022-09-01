package com.xm.crypto.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class CryptoPrice extends Entity {

    private Long date;
    private Crypto crypto;
    private BigDecimal price;
}
