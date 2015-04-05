package com.xeiam.xchange.coinmate;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 *
 * @author Martin Stachon
 */
public class CoinmateExchange extends BaseExchange implements Exchange {
    
    private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

    @Override
    public SynchronizedValueFactory<Long> getNonceFactory() {
        return nonceFactory;
    }

    @Override
    public ExchangeSpecification getDefaultExchangeSpecification() {
        ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
        exchangeSpecification.setSslUri("https://coinmate.io");
        exchangeSpecification.setHost("coinmate.io");
        exchangeSpecification.setPort(80);
        exchangeSpecification.setExchangeName("CoinMate");
        exchangeSpecification.setExchangeDescription("Bitcoin trading made simple.");
        return exchangeSpecification;
    }

}
