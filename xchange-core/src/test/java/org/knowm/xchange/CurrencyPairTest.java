package org.knowm.xchange;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.ObjectMapperHelper;

class CurrencyPairTest {

  @Test
  void majors() {

    assertThat(CurrencyPair.EUR_USD.getBase().getCurrencyCode()).isEqualTo("EUR");
    assertThat(CurrencyPair.EUR_USD.getCounter().getCurrencyCode()).isEqualTo("USD");

    assertThat(CurrencyPair.GBP_USD.getBase().getCurrencyCode()).isEqualTo("GBP");
    assertThat(CurrencyPair.GBP_USD.getCounter().getCurrencyCode()).isEqualTo("USD");

    assertThat(CurrencyPair.USD_JPY.getBase().getCurrencyCode()).isEqualTo("USD");
    assertThat(CurrencyPair.USD_JPY.getCounter().getCurrencyCode()).isEqualTo("JPY");

    assertThat(CurrencyPair.USD_CHF.getBase().getCurrencyCode()).isEqualTo("USD");
    assertThat(CurrencyPair.USD_CHF.getCounter().getCurrencyCode()).isEqualTo("CHF");

    assertThat(CurrencyPair.USD_AUD.getBase().getCurrencyCode()).isEqualTo("USD");
    assertThat(CurrencyPair.USD_AUD.getCounter().getCurrencyCode()).isEqualTo("AUD");

    assertThat(CurrencyPair.USD_CAD.getBase().getCurrencyCode()).isEqualTo("USD");
    assertThat(CurrencyPair.USD_CAD.getCounter().getCurrencyCode()).isEqualTo("CAD");
  }

  @Test
  void bitcoinCourtesy() {

    assertThat(CurrencyPair.BTC_USD.getBase().getCurrencyCode()).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_USD.getCounter().getCurrencyCode()).isEqualTo("USD");

    assertThat(CurrencyPair.BTC_GBP.getBase().getCurrencyCode()).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_USD.getCounter().getCurrencyCode()).isEqualTo("USD");

    assertThat(CurrencyPair.BTC_EUR.getBase().getCurrencyCode()).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_EUR.getCounter().getCurrencyCode()).isEqualTo("EUR");

    assertThat(CurrencyPair.BTC_JPY.getBase().getCurrencyCode()).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_JPY.getCounter().getCurrencyCode()).isEqualTo("JPY");

    assertThat(CurrencyPair.BTC_CHF.getBase().getCurrencyCode()).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_CHF.getCounter().getCurrencyCode()).isEqualTo("CHF");

    assertThat(CurrencyPair.BTC_AUD.getBase().getCurrencyCode()).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_AUD.getCounter().getCurrencyCode()).isEqualTo("AUD");

    assertThat(CurrencyPair.BTC_CAD.getBase().getCurrencyCode()).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_CAD.getCounter().getCurrencyCode()).isEqualTo("CAD");
  }

  @Test
  void serializeDeserialize() throws Exception {
    CurrencyPair jsonCopy = ObjectMapperHelper.viaJSON(CurrencyPair.XBT_USD);
    assertThat(jsonCopy).isEqualTo(CurrencyPair.XBT_USD);
  }
}
