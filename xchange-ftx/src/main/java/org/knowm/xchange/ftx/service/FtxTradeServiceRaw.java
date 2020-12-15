package org.knowm.xchange.ftx.service;


import org.knowm.xchange.Exchange;
import org.knowm.xchange.ftx.FtxException;
import org.knowm.xchange.ftx.dto.trade.FtxOrderRequestPOJO;
import org.knowm.xchange.ftx.dto.trade.FtxOrderResponse;

import java.io.IOException;

public class FtxTradeServiceRaw extends FtxBaseService {

    public FtxTradeServiceRaw(Exchange exchange) {
        super(exchange);
    }

    public FtxOrderResponse placeNewFtxOrder(String subAccount, FtxOrderRequestPOJO payload) throws FtxException, IOException {
        return ftx.placeNewOrder(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory().createValue(),
            signatureCreator,
            subAccount,
            payload
        );
    }
}
