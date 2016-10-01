package org.knowm.xchange.coinbaseex;

import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinbaseex.service.polling.CoinbaseExMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;

public class CoinbaseExExchangeTest {

  @Test
  public void coinbaseShouldBeInstantiatedWithoutAnExceptionWhenUsingDefaultSpecification() {
    ExchangeFactory.INSTANCE.createExchange(CoinbaseExExchange.class.getCanonicalName());
  }

  @Test
  public void shouldSupportEthUsdByRemoteInit() throws Exception {
    Exchange ex = ExchangeFactory.INSTANCE.createExchange(CoinbaseExExchange.class.getCanonicalName());
    ex.remoteInit();
    Assert.assertTrue(((CoinbaseExMarketDataServiceRaw) ex.getPollingMarketDataService()).checkProductExists(new CurrencyPair("ETH", "USD")));
  }
}
