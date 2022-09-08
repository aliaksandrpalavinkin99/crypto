package com.xm.crypto.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xm.crypto.entity.Crypto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class CryptoPriceDTO extends EntityDTO {

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date date;
    private Crypto crypto;
    private BigDecimal price;
}
