package org.knowm.xchange.bybit.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.trade.BybitOrderDetails;
import org.knowm.xchange.bybit.dto.trade.BybitOrderRequest;

import java.io.IOException;

public class BybitTradeServiceRaw extends BybitBaseService {

    public BybitTradeServiceRaw(Exchange exchange) {
        super(exchange);
    }

    public BybitResult<BybitOrderDetails> getBybitOrder(String orderId) throws IOException {
        return bybitAuthenticated.getOrder(apiKey, orderId, nonceFactory, signatureCreator);
    }

    public BybitResult<BybitOrderRequest> placeOrder(String symbol, long qty, String side, String type) throws IOException {
        return bybitAuthenticated.placeOrder(
                apiKey,
                symbol,
                qty,
                side,
                type,
                nonceFactory,
                signatureCreator
        );
    }

}
