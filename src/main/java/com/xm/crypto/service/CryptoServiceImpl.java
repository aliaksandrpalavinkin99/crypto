package com.xm.crypto.service;

import com.xm.crypto.entity.CryptoPrice;
import com.xm.crypto.parser.CryptoPriceLoader;
import com.xm.crypto.repository.CryptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CryptoServiceImpl implements CryptoService {

    @Autowired
    private CryptoPriceLoader cryptoPriceLoader;
    @Autowired
    private CryptoRepository cryptoRepository;
    @Override
    public List<CryptoPrice> saveDataToDbFromCSV() {
        List<CryptoPrice> prices = cryptoPriceLoader.parsePrices();
        cryptoRepository.saveAll(prices);

        return prices;
    }
}
