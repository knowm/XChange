package org.knowm.xchange.bybit.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.BybitAuthenticated;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.utils.nonce.CurrentTimeIncrementalNonceFactory;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import java.util.concurrent.TimeUnit;

public class BybitBaseService implements BaseService {

    protected final BybitAuthenticated bybitAuthenticated;
    protected final ParamsDigest signatureCreator;
    protected final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeIncrementalNonceFactory(TimeUnit.MILLISECONDS);
    protected final String apiKey;

    public BybitBaseService(Exchange exchange) {
        bybitAuthenticated = ExchangeRestProxyBuilder.forInterface(BybitAuthenticated.class, exchange.getExchangeSpecification()).build();
        signatureCreator = BybitDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
        apiKey = exchange.getExchangeSpecification().getApiKey();
    }


}
