package org.knowm.xchange.binance.service;

import java.io.IOException;
import java.math.BigDecimal;

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
    
    
    public Object withdraw(String asset, String address, BigDecimal amount, String name, Long recvWindow
            , long timestamp) throws IOException, BinanceException {
        return binance.withdraw(asset, address, amount, name, recvWindow, timestamp, super.apiKey, super.signatureCreator);
    }
    
    public Object depositHistory(String asset, Long startTime, Long endTime, Long recvWindow, long timestamp) throws BinanceException, IOException {
        return binance.depositHistory(asset, startTime, endTime, recvWindow, timestamp, super.apiKey, super.signatureCreator);
    }
    
    public Object withdrawHistory(String asset, Long startTime, Long endTime, Long recvWindow, long timestamp) throws BinanceException, IOException {
        return binance.withdrawHistory(asset, startTime, endTime, recvWindow, timestamp, super.apiKey, super.signatureCreator);
    }
    
}
