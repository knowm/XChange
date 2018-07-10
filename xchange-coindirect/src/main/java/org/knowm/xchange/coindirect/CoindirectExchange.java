package org.knowm.xchange.coindirect;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coindirect.service.CoindirectMarketDataService;
import org.knowm.xchange.utils.AuthUtils;
import org.knowm.xchange.utils.nonce.AtomicLongCurrentTimeIncrementalNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoindirectExchange extends BaseExchange {
    private SynchronizedValueFactory<Long> nonceFactory =
            new AtomicLongCurrentTimeIncrementalNonceFactory();

    @Override
    protected void initServices() {
        this.marketDataService = new CoindirectMarketDataService(this);
    }

    @Override
    public SynchronizedValueFactory<Long> getNonceFactory() {
        return nonceFactory;
    }

    @Override
    public ExchangeSpecification getDefaultExchangeSpecification() {
        ExchangeSpecification spec = new ExchangeSpecification(this.getClass().getCanonicalName());
        spec.setSslUri("https://api.coindirect.com");
        spec.setHost("www.coindirect.com");
        spec.setPort(80);
        spec.setExchangeName("Coindirect");
        spec.setExchangeDescription("Coindirect Exchange.");
        AuthUtils.setApiAndSecretKey(spec, "coindirect");
        return spec;
    }
}
