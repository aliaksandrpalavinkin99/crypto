package com.xm.crypto.service;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import com.xm.crypto.entity.ComparedResult;
import com.xm.crypto.entity.Crypto;
import com.xm.crypto.entity.CryptoPrice;
import com.xm.crypto.entity.NormalizedRange;
import com.xm.crypto.exception.NoDataException;
import com.xm.crypto.parser.CryptoPriceLoader;
import com.xm.crypto.repository.CryptoRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
public class CryptoServiceImplTest {

    private static final Long DATE = 1641009600000L;

    @Mock
    private CryptoRepository repository;

    @Mock
    private CryptoPriceLoader cryptoPriceLoader;

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
}
