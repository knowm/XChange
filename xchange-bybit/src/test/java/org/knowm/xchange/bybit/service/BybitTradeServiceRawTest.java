package org.knowm.xchange.bybit.service;

import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.trade.BybitOrderDetails;
import org.knowm.xchange.bybit.dto.trade.BybitOrderRequest;

import java.io.IOException;

public class BybitTradeServiceRawTest {

    @Test
    public void testGetBybitOrder() throws IOException {
        BybitExchange bybitExchange = new BybitExchange();
        ExchangeSpecification exchangeSpecification = bybitExchange.getDefaultExchangeSpecification();
        exchangeSpecification.setApiKey(System.getenv("API_KEY"));
        exchangeSpecification.setSecretKey(System.getenv("SECRET_KEY"));
        exchangeSpecification.setShouldLoadRemoteMetaData(false);
        exchangeSpecification.setMetaDataJsonFileOverride(null);

        bybitExchange.applySpecification(exchangeSpecification);

        BybitTradeServiceRaw bybitAccountServiceRaw = new BybitTradeServiceRaw(bybitExchange);
        BybitResult<BybitOrderDetails> order = bybitAccountServiceRaw.getBybitOrder("");
        System.out.println(order);
    }

    @Test
    public void testPlaceBybitOrder() throws IOException {
        BybitExchange bybitExchange = new BybitExchange();
        ExchangeSpecification exchangeSpecification = bybitExchange.getDefaultExchangeSpecification();
        exchangeSpecification.setApiKey(System.getenv("API_KEY"));
        exchangeSpecification.setSecretKey(System.getenv("SECRET_KEY"));
        exchangeSpecification.setShouldLoadRemoteMetaData(false);
        exchangeSpecification.setMetaDataJsonFileOverride(null);

        bybitExchange.applySpecification(exchangeSpecification);

        BybitTradeServiceRaw bybitAccountServiceRaw = new BybitTradeServiceRaw(bybitExchange);
        BybitResult<BybitOrderRequest> order = bybitAccountServiceRaw.placeOrder(
                "",
                300,
                "SELL",
                "MARKET"
        );
        System.out.println(order);
    }

}
