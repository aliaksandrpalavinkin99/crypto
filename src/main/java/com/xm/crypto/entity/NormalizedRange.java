package com.xm.crypto.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NormalizedRange {

    @Enumerated(EnumType.STRING)
    private Crypto crypto;
    private BigDecimal price;
}
