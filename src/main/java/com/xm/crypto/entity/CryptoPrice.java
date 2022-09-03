package com.xm.crypto.entity;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@jakarta.persistence.Entity
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
