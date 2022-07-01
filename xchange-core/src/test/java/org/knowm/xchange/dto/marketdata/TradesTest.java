package org.knowm.xchange.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.utils.ObjectMapperHelper;

public class TradesTest {

  @Test
  public void TradeIDComparator() {
    Trade t1 = new Trade.Builder().id("99").build();
    Trade t2 = new Trade.Builder().id("100").build();
    Trade t3 = new Trade.Builder().id("abc").build();
    Trade t4 = new Trade.Builder().id("zzz").build();
    Assert.assertTrue(new Trades.TradeIDComparator().compare(t1, t2) < 0);
    Assert.assertTrue(new Trades.TradeIDComparator().compare(t1, t3) < 0);
    Assert.assertTrue(new Trades.TradeIDComparator().compare(t2, t3) < 0);
    try {
      new Trades.TradeIDComparator().compare(t1, t3);
      new Trades.TradeIDComparator().compare(t3, t1);
      new Trades.TradeIDComparator().compare(t3, t4);
    } catch (Exception e) {
      Assert.fail("Could not compare trades");
    }
  }

  @Test
  public void testSerializeDeserialize() throws IOException {
    Trade t1 =
        new Trade.Builder()
            .currencyPair(CurrencyPair.BTC_CAD)
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
