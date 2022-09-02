package com.xm.crypto.parser;

import com.xm.crypto.entity.CryptoPrice;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

public class CryptoPriceLoaderImpl implements CryptoPriceLoader {

    @Autowired
    private CryptoPriceParser parser;

    @Override
    public List<CryptoPrice> parsePrices() {
        if (parser.isParseNeeded()) {
            return parser.parse();
        }

        return Collections.emptyList();
    }
}
