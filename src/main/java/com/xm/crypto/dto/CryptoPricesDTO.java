package com.xm.crypto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CryptoPricesDTO {

    private List<CryptoPriceDTO> price;
}
