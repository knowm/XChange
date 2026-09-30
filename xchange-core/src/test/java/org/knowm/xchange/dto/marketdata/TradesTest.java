package org.knowm.xchange.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.utils.ObjectMapperHelper;

class TradesTest {

  @Test
  void TradeIDComparator() {
    Trade t1 = Trade.builder().id("99").build();
    Trade t2 = Trade.builder().id("100").build();
    Trade t3 = Trade.builder().id("abc").build();
    Trade t4 = Trade.builder().id("zzz").build();
    assertThat(new Trades.TradeIDComparator().compare(t1, t2) < 0).isTrue();
    assertThat(new Trades.TradeIDComparator().compare(t1, t3) < 0).isTrue();
    assertThat(new Trades.TradeIDComparator().compare(t2, t3) < 0).isTrue();
    Assertions.assertDoesNotThrow(
        () -> {
          new Trades.TradeIDComparator().compare(t1, t3);
          new Trades.TradeIDComparator().compare(t3, t1);
          new Trades.TradeIDComparator().compare(t3, t4);
        },
        "Could not compare trades");
  }

  @Test
  void serializeDeserialize() throws Exception {
    Trade t1 =
        Trade.builder()
            .instrument(CurrencyPair.BTC_CAD)
            .id("BAR")
            .originalAmount(new BigDecimal("0.12"))
            .price(new BigDecimal("0.13"))
            .timestamp(new Date())
            .type(OrderType.BID)
            .takerOrderId("taker1")
            .makerOrderId("maker1")
            .build();

    Trade jsonCopy = ObjectMapperHelper.viaJSON(t1);
    assertThat(jsonCopy).isEqualTo(t1);
  }
}
