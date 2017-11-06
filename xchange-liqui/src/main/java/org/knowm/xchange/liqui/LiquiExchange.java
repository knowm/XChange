package org.knowm.xchange.liqui;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.utils.nonce.CurrentTime1000NonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class LiquiExchange extends BaseExchange implements Exchange {

    // TODO do we need this?
    private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTime1000NonceFactory();

    public LiquiExchange() {
    }

    @Override
    protected void initServices() {
    }

    @Override
    public SynchronizedValueFactory<Long> getNonceFactory() {
        return nonceFactory;
    }

    @Override
    public ExchangeSpecification getDefaultExchangeSpecification() {
        final ExchangeSpecification spec = new ExchangeSpecification(getClass().getCanonicalName());
        spec.setSslUri("https://api.liqui.io/");
        spec.setHost("api.liqui.io");
        spec.setPort(80);
        spec.setExchangeName("Liqui.io");
        spec.setExchangeDescription("Liqui.io Exchange.");
        return spec;
    }
}
