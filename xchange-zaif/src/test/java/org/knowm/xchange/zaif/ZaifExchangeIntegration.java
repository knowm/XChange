package org.knowm.xchange.zaif;

import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.zaif.service.ZaifMarketDataServiceRaw;

public class ZaifExchangeIntegration {

  @Test
  public void shouldBeInstantiatedWithoutAnExceptionWhenUsingDefaultSpecification() {
    ExchangeFactory.INSTANCE.createExchange(ZaifExchange.class.getCanonicalName());
  }

  @Test
  public void shouldSupportBitCrystalOnlyByRemoteInit() {

    Exchange ex = ExchangeFactory.INSTANCE.createExchange(ZaifExchange.class.getCanonicalName());
    // ex.remoteInit();

    Assert.assertTrue(
        ((ZaifMarketDataServiceRaw) ex.getMarketDataService())
            .checkProductExists(new CurrencyPair("BITCRYSTALS/JPY")));
  }
}
