package org.knowm.xchange.bybit.service;

import org.knowm.xchange.bybit.Bybit;
import org.knowm.xchange.bybit.BybitAuthenticated;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;

public class BybitBaseService implements BaseService {

    protected final Bybit bybit;
    protected final BybitAuthenticated bybitAuthenticated;
    protected final ParamsDigest signatureCreator;
    protected final String apiKey;
    protected final String passphrase;

    public BybitBaseService(BybitExchange exchange) {
        bybit = ExchangeRestProxyBuilder.forInterface(Bybit.class, exchange.getExchangeSpecification()).build();
        bybitAuthenticated = ExchangeRestProxyBuilder.forInterface(BybitAuthenticated.class, exchange.getExchangeSpecification()).build();

        signatureCreator = BybitDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
        apiKey = exchange.getExchangeSpecification().getApiKey();
        passphrase = (String) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("passphrase");
    }
}
