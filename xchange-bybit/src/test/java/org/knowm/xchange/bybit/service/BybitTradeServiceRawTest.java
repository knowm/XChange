package org.knowm.xchange.bybit.service;

import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.BybitBalances;
import org.knowm.xchange.bybit.dto.trade.BybitOrder;

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
        BybitResult<BybitOrder> order = bybitAccountServiceRaw.getBybitOrder("");
        System.out.println(order);
    }
}
