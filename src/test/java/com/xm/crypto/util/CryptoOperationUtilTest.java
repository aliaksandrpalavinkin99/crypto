package com.xm.crypto.util;

import static org.mockito.Mockito.when;

import com.xm.crypto.entity.Crypto;
import com.xm.crypto.entity.CryptoPrice;
import com.xm.crypto.entity.OperationType;
import com.xm.crypto.repository.CryptoRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
public class CryptoOperationUtilTest {

    @Mock
    private CryptoRepository repository;

    @InjectMocks
    private CryptoOperationUtil util;

    @Test
    public void givenOperationTypeMin_whenGetQueryMethodByOperation_thenSuccess() {
        //given
        OperationType operationType = OperationType.MIN;
        Crypto crypto = Crypto.BTC;
        CryptoPrice cryptoPrice = new CryptoPrice(1641009600000L, Crypto.BTC, new BigDecimal("46813.21"));
        //when
        when(repository.findTop1ByCryptoOrderByPriceAsc(crypto)).thenReturn(cryptoPrice);
        //then
        CryptoPrice result = util.getQueryMethodByOperation(operationType).apply(crypto);
        Assert.assertEquals(result, cryptoPrice);
    }

    @Test
    public void givenOperationTypeMax_whenGetQueryMethodByOperation_thenSuccess() {
        //given
        OperationType operationType = OperationType.MAX;
        Crypto crypto = Crypto.BTC;
        CryptoPrice cryptoPrice = new CryptoPrice(1641009600000L, Crypto.BTC, new BigDecimal("46813.21"));
        //when
        when(repository.findTop1ByCryptoOrderByPriceDesc(crypto)).thenReturn(cryptoPrice);
        //then
        CryptoPrice result = util.getQueryMethodByOperation(operationType).apply(crypto);
        Assert.assertEquals(result, cryptoPrice);
    }

    @Test
    public void givenOperationTypeLatest_whenGetQueryMethodByOperation_thenSuccess() {
        //given
        OperationType operationType = OperationType.LATEST;
        Crypto crypto = Crypto.BTC;
        CryptoPrice cryptoPrice = new CryptoPrice(1641009600000L, Crypto.BTC, new BigDecimal("46813.21"));
        //when
        when(repository.findTop1ByCryptoOrderByDateDesc(crypto)).thenReturn(cryptoPrice);
        //then
        CryptoPrice result = util.getQueryMethodByOperation(operationType).apply(crypto);
        Assert.assertEquals(result, cryptoPrice);
    }

    @Test
    public void givenOperationTypeNewest_whenGetQueryMethodByOperation_thenSuccess() {
        //given
        OperationType operationType = OperationType.NEWEST;
        Crypto crypto = Crypto.BTC;
        CryptoPrice cryptoPrice = new CryptoPrice(1641009600000L, Crypto.BTC, new BigDecimal("46813.21"));
        //when
        when(repository.findTop1ByCryptoOrderByDateAsc(crypto)).thenReturn(cryptoPrice);
        //then
        CryptoPrice result = util.getQueryMethodByOperation(operationType).apply(crypto);
        Assert.assertEquals(result, cryptoPrice);
    }

    @Test
    public void givenOperationTypeNewest_whenGetOperationMethodForMonth_thenSuccess() {
        //given
        OperationType operationType = OperationType.NEWEST;
        List<CryptoPrice> cryptoPrice =
                List.of(new CryptoPrice(1641009600000L, Crypto.BTC, new BigDecimal("46813.21")));
        //when
        when(repository.findNewestPricesForEachCrypto(getStartMonthTimestamp(), getEndMonthTimestamp()))
                .thenReturn(cryptoPrice);
        //then
        List<CryptoPrice> result =
                util.getOperationMethodForMonth(operationType).apply(getStartMonthTimestamp(), getEndMonthTimestamp());
        Assert.assertEquals(result, cryptoPrice);
    }

    @Test
    public void givenOperationTypeLatest_whenGetOperationMethodForMonth_thenSuccess() {
        //given
        OperationType operationType = OperationType.LATEST;
        List<CryptoPrice> cryptoPrice =
                List.of(new CryptoPrice(1641009600000L, Crypto.BTC, new BigDecimal("46813.21")));
        //when
        when(repository.findLatestPricesForEachCrypto(getStartMonthTimestamp(), getEndMonthTimestamp()))
                .thenReturn(cryptoPrice);
        //then
        List<CryptoPrice> result =
                util.getOperationMethodForMonth(operationType).apply(getStartMonthTimestamp(), getEndMonthTimestamp());
        Assert.assertEquals(result, cryptoPrice);
    }

    @Test
    public void givenOperationTypeMin_whenGetOperationMethodForMonth_thenSuccess() {
        //given
        OperationType operationType = OperationType.MIN;
        List<CryptoPrice> cryptoPrice =
                List.of(new CryptoPrice(1641009600000L, Crypto.BTC, new BigDecimal("46813.21")));
        //when
        when(repository.findMinPricesForEachCrypto(getStartMonthTimestamp(), getEndMonthTimestamp()))
                .thenReturn(cryptoPrice);
        //then
        List<CryptoPrice> result =
                util.getOperationMethodForMonth(operationType).apply(getStartMonthTimestamp(), getEndMonthTimestamp());
        Assert.assertEquals(result, cryptoPrice);
    }

    @Test
    public void givenOperationTypeMax_whenGetOperationMethodForMonth_thenSuccess() {
        //given
        OperationType operationType = OperationType.MAX;
        List<CryptoPrice> cryptoPrice =
                List.of(new CryptoPrice(1641009600000L, Crypto.BTC, new BigDecimal("46813.21")));
        //when
        when(repository.findMaxPricesForEachCrypto(getStartMonthTimestamp(), getEndMonthTimestamp()))
                .thenReturn(cryptoPrice);
        //then
        List<CryptoPrice> result =
                util.getOperationMethodForMonth(operationType).apply(getStartMonthTimestamp(), getEndMonthTimestamp());
        Assert.assertEquals(result, cryptoPrice);
    }

    private Long getStartMonthTimestamp() {
        return 1641009600000L;
    }

    private Long getEndMonthTimestamp() {
        return 1841009600000L;
    }
}
