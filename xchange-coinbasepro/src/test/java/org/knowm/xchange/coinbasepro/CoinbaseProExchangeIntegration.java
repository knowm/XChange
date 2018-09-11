package org.knowm.xchange.coinbasepro;

import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinbasepro.service.CoinbaseProMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;

public class CoinbaseProExchangeIntegration {

  @Test
  public void coinbaseShouldBeInstantiatedWithoutAnExceptionWhenUsingDefaultSpecification() {

    ExchangeFactory.INSTANCE.createExchange(CoinbaseProExchange.class.getCanonicalName());
  }

  @Test
  public void shouldSupportEthUsdByRemoteInit() throws Exception {

    Exchange ex =
        ExchangeFactory.INSTANCE.createExchange(CoinbaseProExchange.class.getCanonicalName());
    ex.remoteInit();
    Assert.assertTrue(
        ((CoinbaseProMarketDataServiceRaw) ex.getMarketDataService())
            .checkProductExists(new CurrencyPair("ETH", "USD")));
  }
}
