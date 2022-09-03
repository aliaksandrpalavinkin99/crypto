package com.xm.crypto.service;

import com.xm.crypto.entity.Crypto;
import com.xm.crypto.entity.CryptoPrice;
import com.xm.crypto.entity.OperationType;
import com.xm.crypto.parser.CryptoPriceLoader;
import com.xm.crypto.repository.CryptoRepository;
import com.xm.crypto.util.CryptoOperationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public CryptoPrice getByCryptoTypeAndOperation(Crypto crypto, OperationType operationType) {
        saveDataToDbFromCSV();

        return operationUtil.getQueryMethodByOperation(operationType).apply(crypto);
    }

    private void saveDataToDbFromCSV() {
        List<CryptoPrice> prices = cryptoPriceLoader.parsePrices();
        cryptoRepository.saveAll(prices);
    }
}
