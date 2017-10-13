package org.knowm.xchange;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

import junit.framework.Assert;

public class CurrencyPairTest {

  @Test
  public void testMajors() {

    assertThat(CurrencyPair.EUR_USD.base.getCurrencyCode()).isEqualTo("EUR");
    assertThat(CurrencyPair.EUR_USD.counter.getCurrencyCode()).isEqualTo("USD");

    assertThat(CurrencyPair.GBP_USD.base.getCurrencyCode()).isEqualTo("GBP");
    assertThat(CurrencyPair.GBP_USD.counter.getCurrencyCode()).isEqualTo("USD");

    assertThat(CurrencyPair.USD_JPY.base.getCurrencyCode()).isEqualTo("USD");
    assertThat(CurrencyPair.USD_JPY.counter.getCurrencyCode()).isEqualTo("JPY");

    assertThat(CurrencyPair.USD_CHF.base.getCurrencyCode()).isEqualTo("USD");
    assertThat(CurrencyPair.USD_CHF.counter.getCurrencyCode()).isEqualTo("CHF");

    assertThat(CurrencyPair.USD_AUD.base.getCurrencyCode()).isEqualTo("USD");
    assertThat(CurrencyPair.USD_AUD.counter.getCurrencyCode()).isEqualTo("AUD");

    assertThat(CurrencyPair.USD_CAD.base.getCurrencyCode()).isEqualTo("USD");
    assertThat(CurrencyPair.USD_CAD.counter.getCurrencyCode()).isEqualTo("CAD");
  }

  @Test
  public void testBitcoinCourtesy() {

    assertThat(CurrencyPair.BTC_USD.base.getCurrencyCode()).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_USD.counter.getCurrencyCode()).isEqualTo("USD");

    assertThat(CurrencyPair.BTC_GBP.base.getCurrencyCode()).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_USD.counter.getCurrencyCode()).isEqualTo("USD");

    assertThat(CurrencyPair.BTC_EUR.base.getCurrencyCode()).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_EUR.counter.getCurrencyCode()).isEqualTo("EUR");

    assertThat(CurrencyPair.BTC_JPY.base.getCurrencyCode()).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_JPY.counter.getCurrencyCode()).isEqualTo("JPY");

    assertThat(CurrencyPair.BTC_CHF.base.getCurrencyCode()).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_CHF.counter.getCurrencyCode()).isEqualTo("CHF");

    assertThat(CurrencyPair.BTC_AUD.base.getCurrencyCode()).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_AUD.counter.getCurrencyCode()).isEqualTo("AUD");

    assertThat(CurrencyPair.BTC_CAD.base.getCurrencyCode()).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_CAD.counter.getCurrencyCode()).isEqualTo("CAD");

  }

  @Test
  public void testParseCurrencyPair() {
    assertEquals(CurrencyPair.parseCurrencyPair("ETHUSDT"), new CurrencyPair(Currency.ETH, Currency.USDT));
    assertEquals(CurrencyPair.parseCurrencyPair("ETHUSD"), new CurrencyPair(Currency.ETH, Currency.USD));
    assertEquals(CurrencyPair.parseCurrencyPair("USDTETH"), new CurrencyPair(Currency.USDT, Currency.ETH));
    assertEquals(CurrencyPair.parseCurrencyPair("USDTUSDE"), new CurrencyPair(Currency.USDT, Currency.USDE));
    assertEquals(CurrencyPair.parseCurrencyPair("USDT@@@"), new CurrencyPair(Currency.USDT, new Currency("@@@")));
    assertEquals(CurrencyPair.parseCurrencyPair("@@@USDT"), new CurrencyPair(new Currency("@@@"),(Currency.USDT)));
    assertEquals(CurrencyPair.parseCurrencyPair("@@@@USDT"), new CurrencyPair(new Currency("@@@@"),(Currency.USDT)));
    assertEquals(CurrencyPair.parseCurrencyPair("UNKNOWN"), new CurrencyPair(new Currency("UNKN"), new Currency("OWN")));
  }

}
