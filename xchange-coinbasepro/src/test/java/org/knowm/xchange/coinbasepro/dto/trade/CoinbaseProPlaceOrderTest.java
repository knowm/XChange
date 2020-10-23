package org.knowm.xchange.coinbasepro.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.coinbasepro.CoinbaseProAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;

public class CoinbaseProPlaceOrderTest {

  @Test
  public void nullflagsTest() {
    LimitOrder limitOrder =
        new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD)
            .limitPrice(BigDecimal.ZERO)
            .build();

    CoinbaseProPlaceLimitOrder orderFlagsNull =
        CoinbaseProAdapters.adaptCoinbaseProPlaceLimitOrder(limitOrder);
    assertThat(orderFlagsNull.getPostOnly()).isEqualTo(null);
    assertThat(orderFlagsNull.getTimeInForce()).isEqualTo(null);
  }

  @Test
  public void fillOrKillflagTest() {
    LimitOrder order =
        new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD)
            .limitPrice(BigDecimal.ZERO)
            .flag(CoinbaseProOrderFlags.FILL_OR_KILL)
            .build();

    CoinbaseProPlaceLimitOrder orderFOK =
        CoinbaseProAdapters.adaptCoinbaseProPlaceLimitOrder(order);

    assertThat(orderFOK.getPostOnly()).isEqualTo(null);
    assertThat(orderFOK.getTimeInForce()).isEqualTo(CoinbaseProPlaceLimitOrder.TimeInForce.FOK);
  }

  @Test
  public void postOnlyflagTest() {
    LimitOrder order =
        new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD)
            .limitPrice(BigDecimal.ZERO)
            .flag(CoinbaseProOrderFlags.POST_ONLY)
            .build();

    CoinbaseProPlaceLimitOrder orderPostOnly =
        CoinbaseProAdapters.adaptCoinbaseProPlaceLimitOrder(order);

    assertThat(orderPostOnly.getPostOnly()).isEqualTo(Boolean.TRUE);
    assertThat(orderPostOnly.getTimeInForce()).isEqualTo(null);
  }

  @Test
  public void immediateOrCancelflagTest() {
    LimitOrder order =
        new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD)
            .limitPrice(BigDecimal.ZERO)
            .flag(CoinbaseProOrderFlags.IMMEDIATE_OR_CANCEL)
            .build();

    CoinbaseProPlaceLimitOrder orderIOC =
        CoinbaseProAdapters.adaptCoinbaseProPlaceLimitOrder(order);

    assertThat(orderIOC.getPostOnly()).isEqualTo(null);
    assertThat(orderIOC.getTimeInForce()).isEqualTo(CoinbaseProPlaceLimitOrder.TimeInForce.IOC);
  }
}
