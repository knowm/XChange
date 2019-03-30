package org.knowm.xchange.globitex.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.globitex.dto.trade.GlobitexUserTrades;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsAll;
import si.mazi.rescu.HttpStatusIOException;

import java.io.IOException;

public class GlobitexTradeServiceRaw extends GlobitexBaseService {

    public GlobitexTradeServiceRaw(Exchange exchange) {
        super(exchange);
    }

    public GlobitexUserTrades getGlobitexUserTrades(TradeHistoryParamsAll params) throws IOException {
        try{

            return globitex.getTradeHistory(
                    exchange.getExchangeSpecification().getApiKey(),
                    exchange.getNonceFactory(),
                    signatureCreator,
                    "ts",
                    0,
                    params.getLimit(),
                    params.getCurrencyPair().toString(),
                    "MAK239A01"
            );

        }catch (HttpStatusIOException e){
            throw new ExchangeException(e.getHttpBody());
        }
    }
}
