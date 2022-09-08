package com.xm.crypto.entity;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.math.BigDecimal;

@javax.persistence.Entity
@Table(name = "prices")

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class CryptoPrice extends Entity {

    @CsvBindByName(column = "timestamp")
    @Column(name = "date")
    private Long date;
    @CsvBindByName(column = "symbol")
    @Enumerated(EnumType.STRING)
    @Column(name = "crypto")
    private Crypto crypto;
    @CsvBindByName(column = "price")
    @Column(name = "price")
    private BigDecimal price;
}
