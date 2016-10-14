package org.knowm.xchange.bitmarket;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

public class BitMarketUtilsTest {

  @Test
  public void shouldConvertStringToCurrencyPair() {
    assertThat(BitMarketUtils.BitMarketCurrencyPairToCurrencyPair("BTCPLN")).isEqualTo(CurrencyPair.BTC_PLN);
    assertThat(BitMarketUtils.BitMarketCurrencyPairToCurrencyPair("BTCEUR")).isEqualTo(CurrencyPair.BTC_EUR);
    assertThat(BitMarketUtils.BitMarketCurrencyPairToCurrencyPair("LTCPLN")).isEqualTo(new CurrencyPair("LTC", "PLN"));
    assertThat(BitMarketUtils.BitMarketCurrencyPairToCurrencyPair("LTCBTC")).isEqualTo(CurrencyPair.LTC_BTC);
    //    assertThat(BitMarketUtils.BitMarketCurrencyPairToCurrencyPair("LiteMineXBTC"))
    //      .isEqualTo(new CurrencyPair("LiteMineX", "BTC"));
    assertThat(BitMarketUtils.BitMarketCurrencyPairToCurrencyPair("BTCAUD")).isNull();
    assertThat(BitMarketUtils.BitMarketCurrencyPairToCurrencyPair("LTCEUR")).isNull();
    assertThat(BitMarketUtils.BitMarketCurrencyPairToCurrencyPair("LiteMineXEUR")).isNull();
  }

  @Test
  public void shouldConvertCurrencyPairToString() {
    assertThat(BitMarketUtils.CurrencyPairToBitMarketCurrencyPair(CurrencyPair.BTC_PLN)).isEqualTo("BTCPLN");
    assertThat(BitMarketUtils.CurrencyPairToBitMarketCurrencyPair(CurrencyPair.BTC_EUR)).isEqualTo("BTCEUR");
    assertThat(BitMarketUtils.CurrencyPairToBitMarketCurrencyPair(new CurrencyPair("LTC", "PLN"))).isEqualTo("LTCPLN");
    assertThat(BitMarketUtils.CurrencyPairToBitMarketCurrencyPair(CurrencyPair.LTC_BTC)).isEqualTo("LTCBTC");
    //    assertThat(BitMarketUtils.CurrencyPairToBitMarketCurrencyPair(new CurrencyPair("LiteMineX", "BTC")))
    //      .isEqualTo("LiteMineXBTC");
    assertThat(BitMarketUtils.CurrencyPairToBitMarketCurrencyPair(CurrencyPair.BTC_AUD)).isNull();
    assertThat(BitMarketUtils.CurrencyPairToBitMarketCurrencyPair(CurrencyPair.LTC_EUR)).isNull();
    assertThat(BitMarketUtils.CurrencyPairToBitMarketCurrencyPair(new CurrencyPair("LiteMineX", "EUR"))).isNull();
  }

  @Test
  @Ignore("issue #1140 https://github.com/timmolter/XChange/issues/1140")
  public void shouldConvertStringToOrderType() {
    assertThat(BitMarketUtils.BitMarketOrderTypeToOrderType("buy")).isEqualTo(Order.OrderType.BID);
    assertThat(BitMarketUtils.BitMarketOrderTypeToOrderType("sell")).isEqualTo(Order.OrderType.ASK);
  }

  @Test
  @Ignore("issue #1140 https://github.com/timmolter/XChange/issues/1140")
  public void shouldConvertOrderTypeToString() {
    assertThat(BitMarketUtils.OrderTypeToBitMarketOrderType(Order.OrderType.BID)).isEqualTo("buy");
    assertThat(BitMarketUtils.OrderTypeToBitMarketOrderType(Order.OrderType.ASK)).isEqualTo("sell");
  }

}
