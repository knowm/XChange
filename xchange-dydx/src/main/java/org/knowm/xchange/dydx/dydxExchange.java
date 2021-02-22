package org.knowm.xchange.dydx;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;

public class dydxExchange extends BaseExchange {
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Parameters {
        public static final String PARAM_BATCHED = "batch_messages";
    }

    @Override
    public void initServices() {

    }

    @Override
    public ExchangeSpecification getDefaultExchangeSpecification() {

        ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
        exchangeSpecification.setSslUri("https://api.dydx.exchange");
        exchangeSpecification.setHost("api.dydx.exchange");
        exchangeSpecification.setPort(80);
        exchangeSpecification.setExchangeName("dydx");
        exchangeSpecification.setExchangeDescription(
                "dydx Decentralized Exchange");

        exchangeSpecification.setExchangeSpecificParametersItem(Parameters.PARAM_BATCHED, false);

        return exchangeSpecification;
    }

    @Override
    public void applySpecification(ExchangeSpecification exchangeSpecification) {
        super.applySpecification(exchangeSpecification);
    }

}
