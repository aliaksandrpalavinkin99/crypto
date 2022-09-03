package com.xm.crypto.service;

import com.xm.crypto.entity.*;

import java.time.Month;
import java.util.Date;
import java.util.List;

public interface CryptoService {

    List<CryptoPrice> getCryptoPricesSortedDesc();

    List<CryptoPrice> getOperationResultForEachCryptoByMonth(
            Month month, Integer year, OperationType operationType);

    CryptoPrice getByCryptoTypeAndOperation(Crypto crypto, OperationType operationType);

    ComparedResult compareNormalizedRange();

    NormalizedRange highestNormalizedRange(Date date);
}
