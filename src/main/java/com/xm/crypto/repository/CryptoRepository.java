package com.xm.crypto.repository;

import com.xm.crypto.entity.Crypto;
import com.xm.crypto.entity.CryptoPrice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CryptoRepository extends CrudRepository<CryptoPrice, Long> {

    List<CryptoPrice> findAllByOrderByPriceDesc();
    List<CryptoPrice> findByDateBetween(Long startDate, Long endDate);


    CryptoPrice findTop1ByCryptoOrderByPriceAsc(Crypto crypto);
    CryptoPrice findTop1ByCryptoOrderByPriceDesc(Crypto crypto);
    CryptoPrice findTop1ByCryptoOrderByDateAsc(Crypto crypto);
    CryptoPrice findTop1ByCryptoOrderByDateDesc(Crypto crypto);


    @Query(value = "SELECT pr1.* FROM prices pr1 INNER JOIN " +
            "(SELECT pr2.crypto, MAX(pr2.date) AS date FROM prices pr2 " +
            "WHERE pr2.date >= :start AND pr2.date < :end GROUP BY pr2.crypto) pr2 " +
            "ON pr1.crypto = pr2.crypto AND pr1.date = pr2.date " +
            "WHERE pr1.date >= :start AND pr1.date < :end", nativeQuery = true)
    List<CryptoPrice> findNewestPricesForEachCrypto(@Param("start") Long startMonth, @Param("end") Long endMonth);
    @Query(value = "SELECT pr1.* FROM prices pr1 INNER JOIN " +
            "(SELECT pr2.crypto, MIN(pr2.date) AS date FROM prices pr2 " +
            "WHERE pr2.date >= :start AND pr2.date < :end GROUP BY pr2.crypto) pr2 " +
            "ON pr1.crypto = pr2.crypto AND pr1.date = pr2.date " +
            "WHERE pr1.date >= :start AND pr1.date < :end", nativeQuery = true)
    List<CryptoPrice> findLatestPricesForEachCrypto(@Param("start") Long startMonth, @Param("end") Long endMonth);
    @Query(value = "SELECT pr1.* FROM prices pr1 INNER JOIN " +
            "(SELECT pr2.crypto, MIN(pr2.price) AS price FROM prices pr2 " +
            "WHERE pr2.date >= :start AND pr2.date < :end GROUP BY pr2.crypto) pr2 " +
            "ON pr1.crypto = pr2.crypto AND pr1.price = pr2.price " +
            "WHERE pr1.date >= :start AND pr1.date < :end", nativeQuery = true)
    List<CryptoPrice> findMinPricesForEachCrypto(@Param("start") Long startMonth, @Param("end") Long endMonth);
    @Query(value = "SELECT pr1.* FROM prices pr1 INNER JOIN " +
            "(SELECT pr2.crypto, MAX(pr2.price) AS price FROM prices pr2 " +
            "WHERE pr2.date >= :start AND pr2.date < :end GROUP BY pr2.crypto) pr2 " +
            "ON pr1.crypto = pr2.crypto AND pr1.price = pr2.price " +
            "WHERE pr1.date >= :start AND pr1.date < :end", nativeQuery = true)
    List<CryptoPrice> findMaxPricesForEachCrypto(@Param("start") Long startMonth, @Param("end") Long endMonth);
}
