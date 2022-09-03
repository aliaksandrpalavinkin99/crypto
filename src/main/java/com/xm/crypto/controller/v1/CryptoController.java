package com.xm.crypto.controller.v1;

import com.xm.crypto.controller.EndPointPath;
import com.xm.crypto.dto.ComparedResultDTO;
import com.xm.crypto.dto.CryptoPriceDTO;
import com.xm.crypto.dto.CryptoPricesDTO;
import com.xm.crypto.dto.NormalizedRangeDTO;
import com.xm.crypto.entity.Crypto;
import com.xm.crypto.entity.CryptoPrice;
import com.xm.crypto.entity.OperationType;
import com.xm.crypto.service.CryptoService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Month;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = EndPointPath.CRYPTO_PATH)
public class CryptoController {

    @Autowired
    private CryptoService cryptoService;

    @GetMapping
    public CryptoPricesDTO getCryptoPricesDescSort() {
        List<CryptoPrice> prices = cryptoService.getCryptoPricesSortedDesc();
        ModelMapper modelMapper = new ModelMapper();
        List<CryptoPriceDTO> priceDTOs = modelMapper.map(prices, new TypeToken<List<CryptoPriceDTO>>() {}.getType());

        return new CryptoPricesDTO(priceDTOs);
    }

    @GetMapping(value = "/{cryptoType}/{operation}")
    public CryptoPriceDTO getRequestedCryptoByOperation(
            @PathVariable Crypto cryptoType, @PathVariable OperationType operation) {
        CryptoPrice price = cryptoService.getByCryptoTypeAndOperation(cryptoType, operation);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(price, CryptoPriceDTO.class);
    }

    @GetMapping(value = "/{month}/{year}/{operation}")
    public CryptoPricesDTO getOperationResultForEachCrypto(@PathVariable Month month, @PathVariable Integer year,
                                                           @PathVariable OperationType operation) {
        List<CryptoPrice> prices = cryptoService.getOperationResultForEachCryptoByMonth(month, year, operation);
        ModelMapper modelMapper = new ModelMapper();
        List<CryptoPriceDTO> priceDTOs = modelMapper.map(prices, new TypeToken<List<CryptoPriceDTO>>() {}.getType());

        return new CryptoPricesDTO(priceDTOs);
    }

    @GetMapping(value = "/comparing")
    public ComparedResultDTO compareNormalizedRange () {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true);

        return modelMapper.map(cryptoService.compareNormalizedRange(), ComparedResultDTO.class);
    }

    @GetMapping(value = "/highest-normalized-range/{date}")
    public NormalizedRangeDTO highestNormalizedRange(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(cryptoService.highestNormalizedRange(date), NormalizedRangeDTO.class);
    }
}
