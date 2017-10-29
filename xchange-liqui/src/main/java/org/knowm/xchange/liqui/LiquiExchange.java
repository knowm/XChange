package org.knowm.xchange.liqui;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.utils.nonce.AtomicLongCurrentTimeIncrementalNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class LiquiExchange extends BaseExchange implements Exchange {

    // TODO do we need this?
    private final SynchronizedValueFactory<Long> nonceFactory = new AtomicLongCurrentTimeIncrementalNonceFactory();

    public LiquiExchange() {
    }

    @Override
    protected void initServices() {

    }

    @Override
    public SynchronizedValueFactory<Long> getNonceFactory() {
        return this.nonceFactory;
    }

    @Override
    public ExchangeSpecification getDefaultExchangeSpecification() {
        final ExchangeSpecification spec = new ExchangeSpecification(this.getClass().getCanonicalName());
        spec.setSslUri("https://api.liqui.io/");
        spec.setHost("api.liqui.io");
        spec.setPort(80);
        spec.setExchangeName("Liqui.io");
        spec.setExchangeDescription("Liqui.io Exchange.");
        return spec;
    }
}
