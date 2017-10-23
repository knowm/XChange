package org.knowm.xchange.binance.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.account.BinanceAccountInformation;

public class BinanceAccountServiceRaw extends BinanceBaseService {

    public BinanceAccountServiceRaw(Exchange exchange) {
        super(exchange);
    }
    
    public BinanceAccountInformation account(Long recvWindow, long timestamp) throws BinanceException, IOException {
        return binance.account(recvWindow, timestamp, super.apiKey, super.signatureCreator);
    }
    
}
