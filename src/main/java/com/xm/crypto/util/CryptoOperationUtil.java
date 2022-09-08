package com.xm.crypto.util;

import com.xm.crypto.entity.Crypto;
import com.xm.crypto.entity.CryptoPrice;
import com.xm.crypto.entity.OperationType;
import com.xm.crypto.repository.CryptoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CryptoOperationUtil {

    @Autowired
    private CryptoRepository repository;

    public Function<Crypto, CryptoPrice> getQueryMethodByOperation(OperationType operationType) {
        switch (operationType) {
            case MAX -> {
                return cr -> repository.findTop1ByCryptoOrderByPriceDesc(cr);
            }
            case LATEST -> {
                return cr -> repository.findTop1ByCryptoOrderByDateDesc(cr);
            }
            case NEWEST -> {
                return cr -> repository.findTop1ByCryptoOrderByDateAsc(cr);
            }
            default -> {
                return cr -> repository.findTop1ByCryptoOrderByPriceAsc(cr);
            }
        }
    }

    public BiFunction<Long, Long, List<CryptoPrice>> getOperationMethodForMonth(OperationType operationType) {
        switch (operationType) {
            case MAX -> {
                return (start, end) -> repository.findMaxPricesForEachCrypto(start, end);
            }
            case LATEST -> {
                return (start, end) -> repository.findLatestPricesForEachCrypto(start, end);
            }
            case NEWEST -> {
                return (start, end) -> repository.findNewestPricesForEachCrypto(start, end);
            }
            default -> {
                return (start, end) -> repository.findMinPricesForEachCrypto(start, end);
            }
        }
    }
}
