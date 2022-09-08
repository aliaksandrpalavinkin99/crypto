package com.xm.crypto.parser;

import com.opencsv.bean.CsvToBeanBuilder;
import com.xm.crypto.entity.CryptoPrice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CryptoPriceParserImpl implements CryptoPriceParser {

    private final Logger logger = LogManager.getLogger(CryptoPriceParserImpl.class);
    private static final String FILE_PATH = "prices";
    private static final String FILE_PATTERN = "_values.csv";

    @Override
    public boolean isParseNeeded() {
        try {
            File[] files = new ClassPathResource(FILE_PATH).getFile().listFiles();
            if (Objects.isNull(files) || files.length == 0) {
                return false;
            }

            for (File file : files) {
                if (file.getName().endsWith(FILE_PATTERN)) {
                    return true;
                }
            }

            return false;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public List<CryptoPrice> parse() {
        List<CryptoPrice> prices = new ArrayList<>();

        try {
            File[] files = new ClassPathResource(FILE_PATH).getFile().listFiles();

            if (Objects.nonNull(files)) {
                for (File file : files) {
                    if (file.getName().endsWith(FILE_PATTERN)) {
                        FileReader reader = new FileReader(file);

                        prices.addAll(new CsvToBeanBuilder<CryptoPrice>(reader)
                                .withType(CryptoPrice.class)
                                .build()
                                .parse());

                        reader.close();

                        Files.move(file.toPath(), file.toPath().resolveSibling(file.getName() + "_parsed"),
                                StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return prices;
    }
}
