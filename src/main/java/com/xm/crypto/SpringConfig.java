package com.xm.crypto;

import com.xm.crypto.parser.CryptoPriceLoader;
import com.xm.crypto.parser.CryptoPriceLoaderImpl;
import com.xm.crypto.parser.CryptoPriceParser;
import com.xm.crypto.parser.CryptoPriceParserImpl;
import com.xm.crypto.util.CryptoOperationUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public CryptoPriceLoader getCryptoPriceLoader() {
        return new CryptoPriceLoaderImpl();
    }

    @Bean
    public CryptoPriceParser getCryptoPriceParser() {
        return new CryptoPriceParserImpl();
    }

    @Bean
    public CryptoOperationUtil getCryptoOperationUtil() {
        return new CryptoOperationUtil();
    }
}
