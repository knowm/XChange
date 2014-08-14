package com.xeiam.xchange;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import com.xeiam.xchange.currency.CurrencyPair;

public class CurrencyPairTest {

  @Test
  public void testMajors() {

    assertThat(CurrencyPair.EUR_USD.baseSymbol).isEqualTo("EUR");
    assertThat(CurrencyPair.EUR_USD.counterSymbol).isEqualTo("USD");

    assertThat(CurrencyPair.GBP_USD.baseSymbol).isEqualTo("GBP");
    assertThat(CurrencyPair.GBP_USD.counterSymbol).isEqualTo("USD");

    assertThat(CurrencyPair.USD_JPY.baseSymbol).isEqualTo("USD");
    assertThat(CurrencyPair.USD_JPY.counterSymbol).isEqualTo("JPY");

    assertThat(CurrencyPair.USD_CHF.baseSymbol).isEqualTo("USD");
    assertThat(CurrencyPair.USD_CHF.counterSymbol).isEqualTo("CHF");

    assertThat(CurrencyPair.USD_AUD.baseSymbol).isEqualTo("USD");
    assertThat(CurrencyPair.USD_AUD.counterSymbol).isEqualTo("AUD");

    assertThat(CurrencyPair.USD_CAD.baseSymbol).isEqualTo("USD");
    assertThat(CurrencyPair.USD_CAD.counterSymbol).isEqualTo("CAD");
  }

  @Test
  public void testBitcoinCourtesy() {

    assertThat(CurrencyPair.BTC_USD.baseSymbol).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_USD.counterSymbol).isEqualTo("USD");

    assertThat(CurrencyPair.BTC_GBP.baseSymbol).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_USD.counterSymbol).isEqualTo("USD");

    assertThat(CurrencyPair.BTC_EUR.baseSymbol).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_EUR.counterSymbol).isEqualTo("EUR");

    assertThat(CurrencyPair.BTC_JPY.baseSymbol).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_JPY.counterSymbol).isEqualTo("JPY");

    assertThat(CurrencyPair.BTC_CHF.baseSymbol).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_CHF.counterSymbol).isEqualTo("CHF");

    assertThat(CurrencyPair.BTC_AUD.baseSymbol).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_AUD.counterSymbol).isEqualTo("AUD");

    assertThat(CurrencyPair.BTC_CAD.baseSymbol).isEqualTo("BTC");
    assertThat(CurrencyPair.BTC_CAD.counterSymbol).isEqualTo("CAD");

  }

}
