package com.xm.crypto.repository;

import com.xm.crypto.entity.CryptoPrice;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CryptoRepository extends CrudRepository<CryptoPrice, Long> {

    List<CryptoPrice> findAllByOrderByPriceDesc();
}
