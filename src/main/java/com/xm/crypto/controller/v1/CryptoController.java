package com.xm.crypto.controller.v1;

import com.xm.crypto.controller.EndPointPath;
import com.xm.crypto.dto.CryptoPriceDTO;
import com.xm.crypto.dto.CryptoPricesDTO;
import com.xm.crypto.entity.CryptoPrice;
import com.xm.crypto.service.CryptoService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
