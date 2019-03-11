package org.knowm.xchange.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

/** Test class for CurrencyPairDeserializer */
public class CurrencyPairDeserializerTest {

  @Test
  public void testCurrencyPairFromString() {

    CurrencyPair currencyPair;

    currencyPair =
        CurrencyPairDeserializer.getCurrencyPairFromString(CurrencyPair.DOGE_HKD.toString());
    assertThat(currencyPair).isEqualTo(CurrencyPair.DOGE_HKD);

    currencyPair = CurrencyPairDeserializer.getCurrencyPairFromString("BTCUSD");
    assertThat(currencyPair.base).isEqualTo(Currency.BTC);
    assertThat(currencyPair.counter).isEqualTo(Currency.USD);

    currencyPair = CurrencyPairDeserializer.getCurrencyPairFromString("BCBTC");
    assertThat(currencyPair.base).isEqualTo(Currency.BC);
    assertThat(currencyPair.counter).isEqualTo(Currency.BTC);

    currencyPair = CurrencyPairDeserializer.getCurrencyPairFromString("DOGEBTC");
    assertThat(currencyPair.base).isEqualTo(Currency.DOGE);
    assertThat(currencyPair.counter).isEqualTo(Currency.BTC);

    // Current heuristic: CurrencyPairDeserializer takes the end which gives the longer match to a
    // real currency
    currencyPair = CurrencyPairDeserializer.getCurrencyPairFromString("USDEHQXYVBC");
    assertThat(currencyPair.base).isEqualTo(Currency.USDE);
    assertThat(currencyPair.counter.getCurrencyCode()).isEqualTo("HQXYVBC");
  }
}
