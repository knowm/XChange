package org.knowm.xchange.bybit.service;

import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.BybitBalances;

import java.io.IOException;

public class BybitAccountServiceRaw extends BybitBaseService {

    public BybitAccountServiceRaw(BybitExchange exchange) {
        super(exchange);
    }

    public BybitResult<BybitBalances>  getWalletBalances() throws IOException {
        return bybitAuthenticated.getWalletBalances(apiKey, nonceFactory, signatureCreator);
    }

}
