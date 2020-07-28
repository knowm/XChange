package org.knowm.xchange.coinjar;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Date;
import org.junit.Test;
import org.knowm.xchange.coinjar.dto.CoinjarOrder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;

public class CoinjarAdaptersTest {

  @Test
  public void orderTypeToBuySell() {
    assertThat(CoinjarAdapters.buySellToOrderType("buy")).isEqualTo(Order.OrderType.BID);
    assertThat(CoinjarAdapters.buySellToOrderType("sell")).isEqualTo(Order.OrderType.ASK);
  }

  @Test
  public void testProductToCurrencyPair() {
    assertThat(CoinjarAdapters.productToCurrencyPair("BTCAUD")).isEqualTo(CurrencyPair.BTC_AUD);
  }

  @Test
  public void testCurrencyPairToProduct() {
    assertThat(CoinjarAdapters.currencyPairToProduct(CurrencyPair.BTC_AUD)).isEqualTo("BTCAUD");
  }

  @Test
  public void testAdaptStatus() {
    assertThat(CoinjarAdapters.adaptStatus("filled")).isEqualTo(Order.OrderStatus.FILLED);
    assertThat(CoinjarAdapters.adaptStatus("booked")).isEqualTo(Order.OrderStatus.PENDING_NEW);
  }

  @Test
  public void testAdaptOrderToUserTrade() {
    final CoinjarOrder order =
        new CoinjarOrder(
            3267L,
            "LMT",
            "BTCAUD",
            "buy",
            "6203.00000000",
            "0.04000000",
            "GTC",
            "0.04000000",
            "filled",
            null,
            "2017-10-12T15:39:27.000+11:00");

    final UserTrade expected =
        new UserTrade.Builder()
            .id("3267")
            .orderId("3267")
            .currencyPair(CurrencyPair.BTC_AUD)
            .originalAmount(new BigDecimal("0.04000000"))
            .timestamp(Date.from(ZonedDateTime.parse("2017-10-12T15:39:27.000+11:00").toInstant()))
            .price(new BigDecimal("6203.00000000"))
            .build();

    assertThat(CoinjarAdapters.adaptOrderToUserTrade(order)).isEqualTo(expected);
  }
}
