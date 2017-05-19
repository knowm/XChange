package org.knowm.xchange.btcc;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.btcc.service.BTCCMarketDataService;
import org.knowm.xchange.utils.nonce.CurrentNanosecondTimeIncrementalNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class BTCCExchange extends BaseExchange implements Exchange {

    public static final String DATA_API_URI_KEY = "spotusd.data.uri";

    private SynchronizedValueFactory<Long> nonceFactory = new CurrentNanosecondTimeIncrementalNonceFactory();

    @Override
    protected void initServices() {
        this.marketDataService = new BTCCMarketDataService(this);
    }

    @Override
    public SynchronizedValueFactory<Long> getNonceFactory() {
        return nonceFactory;
    }

    @Override
    public ExchangeSpecification getDefaultExchangeSpecification() {
        ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
        exchangeSpecification.setSslUri("https://api.btcc.com");
        exchangeSpecification.setHost("api.btcc.com");
        exchangeSpecification.setPort(80);
        exchangeSpecification.setExchangeName("BTCC");
        exchangeSpecification.setExchangeDescription("BTCC is an USD Crypto Exchange");
        // TODO market data URI is bounded to market symbol right now
        exchangeSpecification.setExchangeSpecificParametersItem(DATA_API_URI_KEY, "https://spotusd-data.btcc.com");
        return exchangeSpecification;
    }
}
