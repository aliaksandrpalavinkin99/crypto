package com.xm.crypto.dto;

import com.xm.crypto.entity.Crypto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class CryptoPriceDTO extends EntityDTO {

    private Long date;
    private Crypto crypto;
    private BigDecimal price;
}
