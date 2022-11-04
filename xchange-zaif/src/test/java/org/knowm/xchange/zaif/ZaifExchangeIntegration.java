package org.knowm.xchange.zaif;

import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.zaif.service.ZaifMarketDataServiceRaw;

public class ZaifExchangeIntegration {

  @Test
  public void shouldBeInstantiatedWithoutAnExceptionWhenUsingDefaultSpecification() {
    ExchangeSpecification spec = new ExchangeSpecification(ZaifExchange.class);
    spec.setProxyHost("localhost");
    spec.setProxyPort(1080);
    ExchangeFactory.INSTANCE.createExchange(spec);
    //    ExchangeFactory.INSTANCE.createExchange(ZaifExchange.class.getCanonicalName());
  }

  @Test
  public void shouldSupportBitCrystalOnlyByRemoteInit() {
    ExchangeSpecification spec = new ExchangeSpecification(ZaifExchange.class);
    spec.setProxyHost("localhost");
    spec.setProxyPort(1080);
    Exchange ex = ExchangeFactory.INSTANCE.createExchange(spec);
    //    Exchange ex =
    // ExchangeFactory.INSTANCE.createExchange(ZaifExchange.class.getCanonicalName());
    // ex.remoteInit();

    Assert.assertTrue(
        ((ZaifMarketDataServiceRaw) ex.getMarketDataService())
            .checkProductExists(new CurrencyPair("ETH/JPY")));
  }
}
