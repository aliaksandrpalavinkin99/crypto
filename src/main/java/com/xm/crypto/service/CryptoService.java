package com.xm.crypto.service;

import com.xm.crypto.entity.CryptoPrice;

import java.util.List;

public interface CryptoService {

    //make private method into impl
    List<CryptoPrice> saveDataToDbFromCSV();
}
