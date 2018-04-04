package org.knowm.xchange.gdax;

import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.gdax.service.GDAXMarketDataServiceRaw;

public class GDAXExchangeIntegration {

  @Test
  public void coinbaseShouldBeInstantiatedWithoutAnExceptionWhenUsingDefaultSpecification() {

    ExchangeFactory.INSTANCE.createExchange(GDAXExchange.class.getCanonicalName());
  }

  @Test
  public void shouldSupportEthUsdByRemoteInit() throws Exception {

    Exchange ex = ExchangeFactory.INSTANCE.createExchange(GDAXExchange.class.getCanonicalName());
    ex.remoteInit();
    Assert.assertTrue(
        ((GDAXMarketDataServiceRaw) ex.getMarketDataService())
            .checkProductExists(new CurrencyPair("ETH", "USD")));
  }
}
