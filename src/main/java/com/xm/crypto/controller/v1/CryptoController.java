package com.xm.crypto.controller.v1;

import com.xm.crypto.controller.EndPointPath;
import com.xm.crypto.dto.ComparedResultDTO;
import com.xm.crypto.dto.CryptoPriceDTO;
import com.xm.crypto.dto.CryptoPricesDTO;
import com.xm.crypto.dto.NormalizedRangeDTO;
import com.xm.crypto.entity.ComparedResult;
import com.xm.crypto.entity.Crypto;
import com.xm.crypto.entity.CryptoPrice;
import com.xm.crypto.entity.OperationType;
import com.xm.crypto.service.CryptoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@RequestMapping(value = EndPointPath.CRYPTO)
public class CryptoController {

    @Autowired
    private CryptoService cryptoService;

    @ApiOperation(value = "Return all crypto prices sorted by descending")
    @GetMapping
    public CryptoPricesDTO getCryptoPricesDescSort() {
        List<CryptoPrice> prices = cryptoService.getCryptoPricesSortedDesc();
        ModelMapper modelMapper = new ModelMapper();
        List<CryptoPriceDTO> priceDTOs = modelMapper.map(prices, new TypeToken<List<CryptoPriceDTO>>() {}.getType());

        return new CryptoPricesDTO(priceDTOs);
    }

    @ApiOperation(value = "Return requested crypto according operation type and crypto type")
    @GetMapping(value = "/{cryptoType}/{operation}")
    public CryptoPriceDTO getRequestedCryptoByOperation(
            @PathVariable @ApiParam(name = "cryptoType", value = "Crypto type") Crypto cryptoType,
            @PathVariable @ApiParam(name = "operation", value = "Operation type") OperationType operation) {
        CryptoPrice price = cryptoService.getByCryptoTypeAndOperation(cryptoType, operation);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(price, CryptoPriceDTO.class);
    }

    @ApiOperation(value = "Return info according operation type for each crypto type for whole month")
    @GetMapping(value = "/{month}/{year}/{operation}")
    public CryptoPricesDTO getOperationResultForEachCrypto(
            @PathVariable @ApiParam(name = "month", value = "Month to search") Month month,
            @PathVariable @ApiParam(name = "year", value = "Year to search")Integer year,
            @PathVariable @ApiParam(name = "operation", value = "Operation type")OperationType operation) {
        List<CryptoPrice> prices = cryptoService.getOperationResultForEachCryptoByMonth(month, year, operation);
        ModelMapper modelMapper = new ModelMapper();
        List<CryptoPriceDTO> priceDTOs = modelMapper.map(prices, new TypeToken<List<CryptoPriceDTO>>() {}.getType());

        return new CryptoPricesDTO(priceDTOs);
    }

    @ApiOperation(value = "Calculate and compare normalized range for all cryptos, return the least and greatest ranges")
    @GetMapping(value = "/comparing")
    public ComparedResultDTO compareNormalizedRange () {
        ComparedResult comparedResult = cryptoService.compareNormalizedRange();

        ModelMapper modelMapper = new ModelMapper();
        NormalizedRangeDTO leastRange =
                modelMapper.map(comparedResult.getLeastNormalizedRange(), NormalizedRangeDTO.class);
        NormalizedRangeDTO greatestRange =
                modelMapper.map(comparedResult.getGreatestNormalizedRange(), NormalizedRangeDTO.class);

        return new ComparedResultDTO(leastRange, greatestRange);
    }

    @ApiOperation(value = "Calculate and compare normalized range for all cryptos for a specific fate, " +
            "return the highest range")
    @GetMapping(value = "/highest-normalized-range/{date}")
    public NormalizedRangeDTO highestNormalizedRange(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")
            @ApiParam(name = "date", value = "Date for searching") Date date) {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(cryptoService.highestNormalizedRange(date), NormalizedRangeDTO.class);
    }
}
