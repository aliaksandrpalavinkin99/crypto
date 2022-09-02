package com.xm.crypto.controller.v1;

import com.xm.crypto.controller.EndPointPath;
import com.xm.crypto.dto.CryptoPriceDTO;
import com.xm.crypto.entity.CryptoPrice;
import com.xm.crypto.service.CryptoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = EndPointPath.CRYPTO_PATH)
public class CryptoController {

    @Autowired
    private CryptoService cryptoService;

    @PostMapping(value = "parse")
    public ResponseEntity<List<CryptoPrice>> parse() {
        return new ResponseEntity<>(
                cryptoService.saveDataToDbFromCSV(), HttpStatus.OK);
    }
}
