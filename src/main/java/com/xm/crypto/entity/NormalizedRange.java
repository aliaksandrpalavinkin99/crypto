package com.xm.crypto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NormalizedRange {

    @Enumerated(EnumType.STRING)
    private Crypto crypto;
    private BigDecimal price;
}
