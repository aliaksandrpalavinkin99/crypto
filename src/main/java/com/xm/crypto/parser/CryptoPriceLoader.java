package com.xm.crypto.parser;

import com.xm.crypto.entity.CryptoPrice;

import java.util.List;

public interface CryptoPriceLoader {

    List<CryptoPrice> parsePrices();
}
