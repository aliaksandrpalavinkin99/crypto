package com.xm.crypto.repository;

import com.xm.crypto.entity.Crypto;
import com.xm.crypto.entity.CryptoPrice;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CryptoRepository extends CrudRepository<CryptoPrice, Long> {

    List<CryptoPrice> findAllByOrderByPriceDesc();


    CryptoPrice findTop1ByCryptoOrderByPriceAsc(Crypto crypto);
    CryptoPrice findTop1ByCryptoOrderByPriceDesc(Crypto crypto);
    CryptoPrice findTop1ByCryptoOrderByDateAsc(Crypto crypto);
    CryptoPrice findTop1ByCryptoOrderByDateDesc(Crypto crypto);

}
