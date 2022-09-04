package com.xm.crypto.service;

import com.xm.crypto.entity.*;
import com.xm.crypto.exception.NoDataException;
import com.xm.crypto.parser.CryptoPriceLoader;
import com.xm.crypto.repository.CryptoRepository;
import com.xm.crypto.util.CryptoOperationUtil;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CryptoServiceImpl implements CryptoService {

    @Autowired
    private CryptoPriceLoader cryptoPriceLoader;
    @Autowired
    private CryptoRepository cryptoRepository;
    @Autowired
    private CryptoOperationUtil operationUtil;

    @Override
    public List<CryptoPrice> getCryptoPricesSortedDesc() {
        saveDataToDbFromCSV();

        return cryptoRepository.findAllByOrderByPriceDesc();
    }

    @Override
    public List<CryptoPrice> getOperationResultForEachCryptoByMonth(
            Month month, Integer year, OperationType operationType) {
        saveDataToDbFromCSV();

        return operationUtil.getOperationMethodForMonth(operationType)
                .apply(getStartMonthTimestamp(month, year), getEndMonthTimestamp(month, year));
    }

    @Override
    public CryptoPrice getByCryptoTypeAndOperation(Crypto crypto, OperationType operationType) {
        saveDataToDbFromCSV();

        return operationUtil.getQueryMethodByOperation(operationType).apply(crypto);
    }

    @Override
    public ComparedResult compareNormalizedRange() {
        saveDataToDbFromCSV();

        List<CryptoPrice> prices = IterableUtils.toList(cryptoRepository.findAll());

        List<NormalizedRange> ranges = findNormalizedRanges(prices);

        if (CollectionUtils.isEmpty(ranges)) {
            throw new NoDataException("Doesn't have prices");
        }

        NormalizedRange min = Collections.min(ranges, Comparator.comparing(NormalizedRange::getPrice));
        NormalizedRange max = Collections.max(ranges, Comparator.comparing(NormalizedRange::getPrice));

        return new ComparedResult(min, max);
    }

    @Override
    public NormalizedRange highestNormalizedRange(Date date) {
        saveDataToDbFromCSV();

        List<CryptoPrice> prices = cryptoRepository.findByDateBetween(
                date.getTime(), Date.from(date.toInstant().plus(1, ChronoUnit.DAYS)).getTime());

        List<NormalizedRange> ranges = findNormalizedRanges(prices);

        if (CollectionUtils.isEmpty(ranges)) {
            throw new NoDataException("Doesn't have prices");
        }

        return Collections.max(ranges, Comparator.comparing(NormalizedRange::getPrice));
    }

    private void saveDataToDbFromCSV() {
        List<CryptoPrice> prices = cryptoPriceLoader.parsePrices();
        cryptoRepository.saveAll(prices);
    }

    private List<NormalizedRange> findNormalizedRanges(List<CryptoPrice> prices) {
        Map<Crypto, List<CryptoPrice>> priceMap =
                prices.stream().collect(Collectors.groupingBy(CryptoPrice::getCrypto));

        List<NormalizedRange> ranges = new ArrayList<>();

        priceMap.forEach((key, item) -> {
            BigDecimal minPrice = Collections.min(item, Comparator.comparing(CryptoPrice::getPrice)).getPrice();
            BigDecimal maxPrice = Collections.max(item, Comparator.comparing(CryptoPrice::getPrice)).getPrice();

            ranges.add(new NormalizedRange(key, maxPrice.subtract(minPrice).divide(minPrice, RoundingMode.CEILING)));
        });

        return ranges;
    }

    private Long getStartMonthTimestamp(Month month, Integer year) {
        Calendar start = Calendar.getInstance();
        start.set(Calendar.MONTH, month.getValue() - 1);
        start.set(Calendar.YEAR, year);
        start.set(Calendar.DAY_OF_MONTH, 1);

        return start.getTimeInMillis();
    }

    private Long getEndMonthTimestamp(Month month, Integer year) {
        Calendar end = Calendar.getInstance();
        end.set(Calendar.MONTH, month.getValue() - 1);
        end.set(Calendar.YEAR, year);
        end.set(Calendar.DAY_OF_MONTH, 1);
        end.add(Calendar.MONTH, 1);

        return end.getTimeInMillis();
    }
}
