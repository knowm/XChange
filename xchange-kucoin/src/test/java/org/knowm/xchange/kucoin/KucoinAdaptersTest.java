package org.knowm.xchange.kucoin;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.kucoin.dto.request.OrderCreateApiRequest;

class KucoinAdaptersTest {

  @Test
  void adapt_market_buy_order() {
    OrderCreateApiRequest expected =
        OrderCreateApiRequest.builder()
            .funds(new BigDecimal("15").toPlainString())
            .clientOid("abc")
            .side("buy")
            .symbol("BTC-USDT")
            .type("market")
            .build();

    MarketOrder marketOrder =
        new MarketOrder.Builder(OrderType.BID, CurrencyPair.BTC_USDT)
            .userReference("abc")
            .originalAmount(new BigDecimal("15"))
            .build();

    OrderCreateApiRequest actual = KucoinAdapters.adaptMarketOrder(marketOrder);

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void adapt_market_sell_order() {
    OrderCreateApiRequest expected =
        OrderCreateApiRequest.builder()
            .size(new BigDecimal("0.002").toPlainString())
            .clientOid("abc")
            .side("sell")
            .symbol("BTC-USDT")
            .type("market")
            .build();

    MarketOrder marketOrder =
        new MarketOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USDT)
            .userReference("abc")
            .originalAmount(new BigDecimal("0.002"))
            .build();

    OrderCreateApiRequest actual = KucoinAdapters.adaptMarketOrder(marketOrder);

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }
}
