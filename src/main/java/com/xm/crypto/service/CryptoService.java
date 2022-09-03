package com.xm.crypto.service;

import com.xm.crypto.entity.CryptoPrice;

import java.util.List;

public interface CryptoService {

    List<CryptoPrice> getCryptoPricesSortedDesc();
}
