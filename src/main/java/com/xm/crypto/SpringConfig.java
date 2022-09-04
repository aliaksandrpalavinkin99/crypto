package com.xm.crypto;

import com.xm.crypto.parser.CryptoPriceLoader;
import com.xm.crypto.parser.CryptoPriceLoaderImpl;
import com.xm.crypto.parser.CryptoPriceParser;
import com.xm.crypto.parser.CryptoPriceParserImpl;
import com.xm.crypto.util.CryptoOperationUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
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

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.xm.crypto.controller.v1"))
                .paths(PathSelectors.any())
                .build();
    }
}
