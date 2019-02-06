package org.knowm.xchange.acx.utils;

import static org.assertj.core.api.Java6Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;

public class AcxUtilsTest {

  @Test
  public void testGetAcxMarket() {
    assertThat(AcxUtils.getAcxMarket(CurrencyPair.BTC_AUD)).isEqualTo("btcaud");
  }

  @Test
  public void testGetCurrencyPair() {
    assertThat(AcxUtils.getCurrencyPair("btcaud")).isEqualTo(CurrencyPair.BTC_AUD);
  }
}
