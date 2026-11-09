package org.knowm.xchange.zaif;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.zaif.service.ZaifMarketDataServiceRaw;

class ZaifExchangeIntegration {

  @Test
  void shouldBeInstantiatedWithoutAnExceptionWhenUsingDefaultSpecification() {
    ExchangeFactory.INSTANCE.createExchange(ZaifExchange.class.getCanonicalName());
  }

  @Test
  void shouldSupportBitCrystalOnlyByRemoteInit() {

    Exchange ex = ExchangeFactory.INSTANCE.createExchange(ZaifExchange.class.getCanonicalName());
    // ex.remoteInit();

    assertThat(
            ((ZaifMarketDataServiceRaw) ex.getMarketDataService())
                .checkProductExists(new CurrencyPair("BITCRYSTALS/JPY")))
        .isTrue();
  }
}
