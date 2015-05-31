package com.xeiam.xchange.coinbaseex;

import com.xeiam.xchange.ExchangeFactory;
import org.junit.Test;

public class CoinbaseExExchangeTest {

    @Test
    public void coinbaseShouldBeInstantiatedWithoutAnExceptionWhenUsingDefaultSpecification() {
        ExchangeFactory.INSTANCE.createExchange(CoinbaseExExchange.class.getCanonicalName());
    }
}
