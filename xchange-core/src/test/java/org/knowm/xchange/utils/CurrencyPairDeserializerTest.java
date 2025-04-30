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
    assertThat(currencyPair.getBase()).isEqualTo(Currency.BTC);
    assertThat(currencyPair.getCounter()).isEqualTo(Currency.USD);

    currencyPair = CurrencyPairDeserializer.getCurrencyPairFromString("BCBTC");
    assertThat(currencyPair.getBase()).isEqualTo(Currency.BC);
    assertThat(currencyPair.getCounter()).isEqualTo(Currency.BTC);

    currencyPair = CurrencyPairDeserializer.getCurrencyPairFromString("DOGEBTC");
    assertThat(currencyPair.getBase()).isEqualTo(Currency.DOGE);
    assertThat(currencyPair.getCounter()).isEqualTo(Currency.BTC);

    // Current heuristic: CurrencyPairDeserializer takes the end which gives the longer match to a
    // real currency
    currencyPair = CurrencyPairDeserializer.getCurrencyPairFromString("USDEHQXYVBC");
    assertThat(currencyPair.getBase()).isEqualTo(Currency.USDE);
    assertThat(currencyPair.getCounter().getCurrencyCode()).isEqualTo("HQXYVBC");
  }
}
