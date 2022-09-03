package com.xm.crypto.service;

import com.xm.crypto.entity.Crypto;
import com.xm.crypto.entity.CryptoPrice;
import com.xm.crypto.entity.OperationType;

import java.util.List;

public interface CryptoService {

    List<CryptoPrice> getCryptoPricesSortedDesc();

    CryptoPrice getByCryptoTypeAndOperation(Crypto crypto, OperationType operationType);
}
