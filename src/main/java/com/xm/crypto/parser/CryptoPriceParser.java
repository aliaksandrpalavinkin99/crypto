package com.xm.crypto.parser;

import com.xm.crypto.entity.CryptoPrice;

import java.util.List;

public interface CryptoPriceParser {

    boolean isParseNeeded();
    List<CryptoPrice> parse();
}
