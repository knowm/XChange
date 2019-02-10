package org.knowm.xchange.abucoins;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import org.assertj.core.util.Maps;
import org.junit.Test;
import org.knowm.xchange.abucoins.dto.account.AbucoinsFill;
import org.knowm.xchange.abucoins.dto.account.AbucoinsFills;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;

public class AbucoinsAdaptersAdaptUserTrades {

  @Test
  public void testAdaptUserTrades() {
    AbucoinsFills fills = new AbucoinsFills();
    fills.setResponseHeaders(Maps.newHashMap("AC-BEFORE", Arrays.asList("someNextCursorValue")));
    fills.add(
        new AbucoinsFill(
            "someTradeId",
            "BTC-PLN",
            new BigDecimal("30000"),
            new BigDecimal("2"),
            "someOrderId",
            "2017-09-28T13:08:43Z",
            AbucoinsFill.Liquidity.T,
            AbucoinsOrder.Side.sell));
    UserTrades trades = AbucoinsAdapters.adaptUserTrades(fills);
    assertEquals("someNextCursorValue", trades.getNextPageCursor());
    assertEquals(1, trades.getUserTrades().size());
    UserTrade trade = trades.getUserTrades().get(0);
    assertEquals("someTradeId", trade.getId());
    assertEquals("BTC/PLN", trade.getCurrencyPair().toString());
  }
}
