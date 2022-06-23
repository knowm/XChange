package org.knowm.xchange.bybit.service;

import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.trade.BybitOrderDetails;

import java.io.IOException;

public class BybitTradeServiceRaw extends BybitBaseService {

    public BybitTradeServiceRaw(BybitExchange exchange) {
        super(exchange);
    }

    public BybitResult<BybitOrderDetails> getBybitOrder(String orderId) throws IOException {
        return bybitAuthenticated.getOrder(apiKey, orderId, nonceFactory, signatureCreator);
    }

}
