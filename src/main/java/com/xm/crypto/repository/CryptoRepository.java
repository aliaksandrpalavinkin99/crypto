package com.xm.crypto.repository;

import com.xm.crypto.entity.CryptoPrice;
import org.springframework.data.repository.CrudRepository;

public interface CryptoRepository extends CrudRepository<CryptoPrice, Long> {
}
