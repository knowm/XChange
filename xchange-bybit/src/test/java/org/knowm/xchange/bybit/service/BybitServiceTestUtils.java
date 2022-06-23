package org.knowm.xchange.bybit.service;

import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.BybitExchange;

public class BybitServiceTestUtils {

    public static BybitExchange createBybitExchange() {
        BybitExchange bybitExchange = new BybitExchange();
        ExchangeSpecification exchangeSpecification = bybitExchange.getDefaultExchangeSpecification();
        exchangeSpecification.setApiKey(System.getenv("API_KEY"));
        exchangeSpecification.setSecretKey(System.getenv("SECRET_KEY"));
        exchangeSpecification.setShouldLoadRemoteMetaData(false);
        exchangeSpecification.setMetaDataJsonFileOverride(null);
        bybitExchange.applySpecification(exchangeSpecification);
        return bybitExchange;
    }

}
