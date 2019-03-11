package org.knowm.xchange.bitmarket;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

public class BitMarketUtilsTest {

  @Test
  public void shouldConvertStringToCurrencyPair() {
    assertThat(BitMarketUtils.bitMarketCurrencyPairToCurrencyPair("BTCPLN"))
        .isEqualTo(CurrencyPair.BTC_PLN);
    assertThat(BitMarketUtils.bitMarketCurrencyPairToCurrencyPair("BTCEUR"))
        .isEqualTo(CurrencyPair.BTC_EUR);
    assertThat(BitMarketUtils.bitMarketCurrencyPairToCurrencyPair("LTCPLN"))
        .isEqualTo(new CurrencyPair("LTC", "PLN"));
    assertThat(BitMarketUtils.bitMarketCurrencyPairToCurrencyPair("LTCBTC"))
        .isEqualTo(CurrencyPair.LTC_BTC);
    //    assertThat(BitMarketUtils.BitMarketCurrencyPairToCurrencyPair("LiteMineXBTC"))
    //      .isEqualTo(new CurrencyPair("LiteMineX", "BTC"));
  }

  @Test
  public void shouldConvertCurrencyPairToString() {
    assertThat(BitMarketUtils.currencyPairToBitMarketCurrencyPair(CurrencyPair.BTC_PLN))
        .isEqualTo("BTCPLN");
    assertThat(BitMarketUtils.currencyPairToBitMarketCurrencyPair(CurrencyPair.BTC_EUR))
        .isEqualTo("BTCEUR");
    assertThat(BitMarketUtils.currencyPairToBitMarketCurrencyPair(new CurrencyPair("LTC", "PLN")))
        .isEqualTo("LTCPLN");
    assertThat(BitMarketUtils.currencyPairToBitMarketCurrencyPair(CurrencyPair.LTC_BTC))
        .isEqualTo("LTCBTC");
    //    assertThat(BitMarketUtils.CurrencyPairToBitMarketCurrencyPair(new
    // CurrencyPair("LiteMineX", "BTC")))
    //      .isEqualTo("LiteMineXBTC");
  }

  @Test
  public void shouldConvertStringToOrderType() {
    assertThat(BitMarketUtils.bitMarketOrderTypeToOrderType("buy")).isEqualTo(Order.OrderType.BID);
    assertThat(BitMarketUtils.bitMarketOrderTypeToOrderType("sell")).isEqualTo(Order.OrderType.ASK);
  }

  @Test
  public void shouldConvertOrderTypeToString() {
    assertThat(BitMarketUtils.orderTypeToBitMarketOrderType(Order.OrderType.BID)).isEqualTo("buy");
    assertThat(BitMarketUtils.orderTypeToBitMarketOrderType(Order.OrderType.ASK)).isEqualTo("sell");
  }
}
