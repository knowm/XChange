package org.knowm.xchange.hitbtc.service.trade;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.hitbtc.HitbtcAdapters;

public class HitbtcTradeServiceRawTest {

  @Test
  public void testReadSymbol() throws Exception {

    String id = HitbtcAdapters.createOrderId(new LimitOrder(Order.OrderType.ASK, BigDecimal.ONE, CurrencyPair.BTC_EUR, null, null, BigDecimal.ONE),
        0);

    assertEquals("BTCEUR", HitbtcAdapters.readSymbol(id));
    assertEquals(Order.OrderType.ASK, HitbtcAdapters.readOrderType(id));
  }

  @Test
  public void testReadSymbolDoge() throws Exception {

    String id = HitbtcAdapters.createOrderId(new LimitOrder(Order.OrderType.ASK, BigDecimal.ONE, CurrencyPair.DOGE_BTC, null, null, BigDecimal.ONE),
        0);

    assertEquals("DOGEBTC", HitbtcAdapters.readSymbol(id));
    assertEquals(Order.OrderType.ASK, HitbtcAdapters.readOrderType(id));
  }

}
