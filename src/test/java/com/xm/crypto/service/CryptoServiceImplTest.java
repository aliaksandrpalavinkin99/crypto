package com.xm.crypto.service;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import com.xm.crypto.entity.ComparedResult;
import com.xm.crypto.entity.Crypto;
import com.xm.crypto.entity.CryptoPrice;
import com.xm.crypto.entity.NormalizedRange;
import com.xm.crypto.entity.OperationType;
import com.xm.crypto.exception.NoDataException;
import com.xm.crypto.parser.CryptoPriceLoader;
import com.xm.crypto.repository.CryptoRepository;
import com.xm.crypto.util.CryptoOperationUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@RunWith(SpringRunner.class)
public class CryptoServiceImplTest {

    private static final Long DATE = 1641009600000L;

    @Mock
    private CryptoRepository repository;

    @Mock
    private CryptoPriceLoader cryptoPriceLoader;

    @Mock
    private CryptoOperationUtil util;

    @InjectMocks
    private CryptoServiceImpl service;

    @Test
    public void givenPrices_whenGetHighestNormalizedRange_thenSuccess() {
        //given
        List<CryptoPrice> prices = List.of(
                new CryptoPrice(DATE, Crypto.BTC, new BigDecimal(4)),
                new CryptoPrice(DATE, Crypto.BTC, new BigDecimal(3)),
                new CryptoPrice(DATE, Crypto.BTC, new BigDecimal(2)),
                new CryptoPrice(DATE, Crypto.DOGE, new BigDecimal(10)),
                new CryptoPrice(DATE, Crypto.DOGE, new BigDecimal(9)),
                new CryptoPrice(DATE, Crypto.DOGE, new BigDecimal(10))
        );
        Date date = new Date();
        NormalizedRange expected = new NormalizedRange(Crypto.BTC, new BigDecimal("1.0000"));
        //when
        when(repository.saveAll(any())).thenReturn(prices);
        when(cryptoPriceLoader.parsePrices()).thenReturn(prices);
        when(repository.findByDateBetween(any(), any())).thenReturn(prices);
        //then
        NormalizedRange normalizedRange = service.highestNormalizedRange(date);
        Assert.assertEquals(expected, normalizedRange);
    }

    @Test(expected = NoDataException.class)
    public void givenEmptyPrices_whenGetHighestNormalizedRange_thenThrownException() {
        //given
        List<CryptoPrice> prices = new ArrayList<>();
        Date date = new Date();
        NormalizedRange expected = new NormalizedRange(null, null);
        //when
        when(repository.saveAll(any())).thenReturn(prices);
        when(cryptoPriceLoader.parsePrices()).thenReturn(prices);
        when(repository.findByDateBetween(any(), any())).thenReturn(prices);
        //then
        service.highestNormalizedRange(date);
    }

    @Test
    public void givenPrices_whenCompareNormalizedRange_thenSuccess() {
        //given
        List<CryptoPrice> prices = List.of(
                new CryptoPrice(DATE, Crypto.BTC, new BigDecimal(4)),
                new CryptoPrice(DATE, Crypto.BTC, new BigDecimal(3)),
                new CryptoPrice(DATE, Crypto.BTC, new BigDecimal(2)),
                new CryptoPrice(DATE, Crypto.DOGE, new BigDecimal(10)),
                new CryptoPrice(DATE, Crypto.DOGE, new BigDecimal(10)),
                new CryptoPrice(DATE, Crypto.DOGE, new BigDecimal(10)),
                new CryptoPrice(DATE, Crypto.LTC, new BigDecimal(10)),
                new CryptoPrice(DATE, Crypto.LTC, new BigDecimal(9))
        );
        ComparedResult expected = new ComparedResult(
                new NormalizedRange(Crypto.DOGE, new BigDecimal("0.0000")),
                new NormalizedRange(Crypto.BTC, new BigDecimal("1.0000")));
        //when
        when(repository.saveAll(any())).thenReturn(prices);
        when(cryptoPriceLoader.parsePrices()).thenReturn(prices);
        when(repository.findAll()).thenReturn(prices);
        //then
        ComparedResult result = service.compareNormalizedRange();
        Assert.assertEquals(expected, result);
    }

    @Test(expected = NoDataException.class)
    public void givenEmptyPrices_whenCompareNormalizedRange_thenThrownException() {
        //given
        List<CryptoPrice> prices = new ArrayList<>();
        //when
        when(repository.saveAll(any())).thenReturn(prices);
        when(cryptoPriceLoader.parsePrices()).thenReturn(prices);
        when(repository.findAll()).thenReturn(prices);
        //then
        service.compareNormalizedRange();
    }

    @Test
    public void givenPrices_whenCryptoPricesSortedDesc_thenSuccess() {
        //given
        List<CryptoPrice> prices = List.of(
                new CryptoPrice(DATE, Crypto.BTC, new BigDecimal(4)),
                new CryptoPrice(DATE, Crypto.BTC, new BigDecimal(3)),
                new CryptoPrice(DATE, Crypto.BTC, new BigDecimal(2)),
                new CryptoPrice(DATE, Crypto.DOGE, new BigDecimal(10)),
                new CryptoPrice(DATE, Crypto.DOGE, new BigDecimal(10)),
                new CryptoPrice(DATE, Crypto.DOGE, new BigDecimal(10)),
                new CryptoPrice(DATE, Crypto.LTC, new BigDecimal(10)),
                new CryptoPrice(DATE, Crypto.LTC, new BigDecimal(9))
        );
        //when
        when(repository.saveAll(any())).thenReturn(prices);
        when(cryptoPriceLoader.parsePrices()).thenReturn(prices);
        when(repository.findAll()).thenReturn(prices);
        when(repository.findAllByOrderByPriceDesc()).thenReturn(prices);
        //then
        List<CryptoPrice> result = service.getCryptoPricesSortedDesc();
        Assert.assertEquals(prices, result);
    }

    @Test
    public void givenOperationTypeMin_whenGetOperationResultForEachCryptoByMonth_thenSuccess() {
        //given
        List<CryptoPrice> prices = List.of(
                new CryptoPrice(DATE, Crypto.BTC, new BigDecimal(3))
        );
        BiFunction<Long, Long, List<CryptoPrice>> function
                = (start, end) -> repository.findMinPricesForEachCrypto(start, end);
        //when
        when(repository.saveAll(any())).thenReturn(prices);
        when(cryptoPriceLoader.parsePrices()).thenReturn(prices);
        when(repository.findAll()).thenReturn(prices);
        when(repository.findMinPricesForEachCrypto(any(), any())).thenReturn(prices);
        when(util.getOperationMethodForMonth(OperationType.MIN)).thenReturn(function);
        //then
        List<CryptoPrice> result =
                service.getOperationResultForEachCryptoByMonth(Month.JULY, 2022, OperationType.MIN);
        Assert.assertEquals(prices, result);
    }

    @Test
    public void givenOperationTypeMax_whenGetOperationResultForEachCryptoByMonth_thenSuccess() {
        //given
        List<CryptoPrice> prices = List.of(
                new CryptoPrice(DATE, Crypto.BTC, new BigDecimal(3))
        );
        BiFunction<Long, Long, List<CryptoPrice>> function
                = (start, end) -> repository.findMaxPricesForEachCrypto(start, end);
        //when
        when(repository.saveAll(any())).thenReturn(prices);
        when(cryptoPriceLoader.parsePrices()).thenReturn(prices);
        when(repository.findAll()).thenReturn(prices);
        when(repository.findMaxPricesForEachCrypto(any(), any())).thenReturn(prices);
        when(util.getOperationMethodForMonth(OperationType.MAX)).thenReturn(function);
        //then
        List<CryptoPrice> result =
                service.getOperationResultForEachCryptoByMonth(Month.JULY, 2022, OperationType.MAX);
        Assert.assertEquals(prices, result);
    }

    @Test
    public void givenOperationTypeLatest_whenGetOperationResultForEachCryptoByMonth_thenSuccess() {
        //given
        List<CryptoPrice> prices = List.of(
                new CryptoPrice(DATE, Crypto.BTC, new BigDecimal(3))
        );
        BiFunction<Long, Long, List<CryptoPrice>> function
                = (start, end) -> repository.findLatestPricesForEachCrypto(start, end);
        //when
        when(repository.saveAll(any())).thenReturn(prices);
        when(cryptoPriceLoader.parsePrices()).thenReturn(prices);
        when(repository.findAll()).thenReturn(prices);
        when(repository.findLatestPricesForEachCrypto(any(), any())).thenReturn(prices);
        when(util.getOperationMethodForMonth(OperationType.LATEST)).thenReturn(function);
        //then
        List<CryptoPrice> result =
                service.getOperationResultForEachCryptoByMonth(Month.JULY, 2022, OperationType.LATEST);
        Assert.assertEquals(prices, result);
    }

    @Test
    public void givenOperationTypeNewest_whenGetOperationResultForEachCryptoByMonth_thenSuccess() {
        //given
        List<CryptoPrice> prices = List.of(
                new CryptoPrice(DATE, Crypto.BTC, new BigDecimal(3))
        );
        BiFunction<Long, Long, List<CryptoPrice>> function
                = (start, end) -> repository.findNewestPricesForEachCrypto(start, end);
        //when
        when(repository.saveAll(any())).thenReturn(prices);
        when(cryptoPriceLoader.parsePrices()).thenReturn(prices);
        when(repository.findAll()).thenReturn(prices);
        when(repository.findNewestPricesForEachCrypto(any(), any())).thenReturn(prices);
        when(util.getOperationMethodForMonth(OperationType.NEWEST)).thenReturn(function);
        //then
        List<CryptoPrice> result =
                service.getOperationResultForEachCryptoByMonth(Month.JULY, 2022, OperationType.NEWEST);
        Assert.assertEquals(prices, result);
    }

    @Test
    public void givenOperationTypeMin_whenGetQueryMethodByOperation_thenSuccess() {
        //given
        List<CryptoPrice> prices = List.of(
                new CryptoPrice(DATE, Crypto.BTC, new BigDecimal(3))
        );
        CryptoPrice price = new CryptoPrice(DATE, Crypto.BTC, new BigDecimal(3));

        Function<Crypto, CryptoPrice> function
                = cr -> repository.findTop1ByCryptoOrderByPriceAsc(cr);
        //when
        when(repository.saveAll(any())).thenReturn(prices);
        when(cryptoPriceLoader.parsePrices()).thenReturn(prices);
        when(repository.findAll()).thenReturn(prices);
        when(repository.findTop1ByCryptoOrderByPriceAsc(Crypto.BTC)).thenReturn(price);
        when(util.getQueryMethodByOperation(OperationType.MIN)).thenReturn(function);
        //then
        CryptoPrice result =
                service.getByCryptoTypeAndOperation(Crypto.BTC,  OperationType.MIN);
        Assert.assertEquals(price, result);
    }

    @Test
    public void givenOperationTypeMax_whenGetQueryMethodByOperation_thenSuccess() {
        //given
        List<CryptoPrice> prices = List.of(
                new CryptoPrice(DATE, Crypto.BTC, new BigDecimal(3))
        );
        CryptoPrice price = new CryptoPrice(DATE, Crypto.BTC, new BigDecimal(3));

        Function<Crypto, CryptoPrice> function
                = cr -> repository.findTop1ByCryptoOrderByPriceDesc(cr);
        //when
        when(repository.saveAll(any())).thenReturn(prices);
        when(cryptoPriceLoader.parsePrices()).thenReturn(prices);
        when(repository.findAll()).thenReturn(prices);
        when(repository.findTop1ByCryptoOrderByPriceDesc(Crypto.BTC)).thenReturn(price);
        when(util.getQueryMethodByOperation(OperationType.MAX)).thenReturn(function);
        //then
        CryptoPrice result =
                service.getByCryptoTypeAndOperation(Crypto.BTC,  OperationType.MAX);
        Assert.assertEquals(price, result);
    }

    @Test
    public void givenOperationTypeLatest_whenGetQueryMethodByOperation_thenSuccess() {
        //given
        List<CryptoPrice> prices = List.of(
                new CryptoPrice(DATE, Crypto.BTC, new BigDecimal(3))
        );
        CryptoPrice price = new CryptoPrice(DATE, Crypto.BTC, new BigDecimal(3));

        Function<Crypto, CryptoPrice> function
                = cr -> repository.findTop1ByCryptoOrderByDateDesc(cr);
        //when
        when(repository.saveAll(any())).thenReturn(prices);
        when(cryptoPriceLoader.parsePrices()).thenReturn(prices);
        when(repository.findAll()).thenReturn(prices);
        when(repository.findTop1ByCryptoOrderByDateDesc(Crypto.BTC)).thenReturn(price);
        when(util.getQueryMethodByOperation(OperationType.LATEST)).thenReturn(function);
        //then
        CryptoPrice result =
                service.getByCryptoTypeAndOperation(Crypto.BTC,  OperationType.LATEST);
        Assert.assertEquals(price, result);
    }

    @Test
    public void givenOperationTypeNewest_whenGetQueryMethodByOperation_thenSuccess() {
        //given
        List<CryptoPrice> prices = List.of(
                new CryptoPrice(DATE, Crypto.BTC, new BigDecimal(3))
        );
        CryptoPrice price = new CryptoPrice(DATE, Crypto.BTC, new BigDecimal(3));

        Function<Crypto, CryptoPrice> function
                = cr -> repository.findTop1ByCryptoOrderByDateAsc(cr);
        //when
        when(repository.saveAll(any())).thenReturn(prices);
        when(cryptoPriceLoader.parsePrices()).thenReturn(prices);
        when(repository.findAll()).thenReturn(prices);
        when(repository.findTop1ByCryptoOrderByDateAsc(Crypto.BTC)).thenReturn(price);
        when(util.getQueryMethodByOperation(OperationType.NEWEST)).thenReturn(function);
        //then
        CryptoPrice result =
                service.getByCryptoTypeAndOperation(Crypto.BTC,  OperationType.NEWEST);
        Assert.assertEquals(price, result);
    }
}
