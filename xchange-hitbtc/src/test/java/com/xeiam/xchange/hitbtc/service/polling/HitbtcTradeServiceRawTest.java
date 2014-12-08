package com.xeiam.xchange.hitbtc.service.polling;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import com.xeiam.xchange.hitbtc.HitbtcAdapters;
import org.junit.Test;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;

public class HitbtcTradeServiceRawTest {

  @Test
  public void testReadSymbol() throws Exception {

    String id = HitbtcAdapters.createOrderId(new LimitOrder(Order.OrderType.ASK, BigDecimal.ONE, CurrencyPair.BTC_EUR, null, null, BigDecimal.ONE), (long) 0);

    assertEquals("BTCEUR", HitbtcAdapters.readSymbol(id));
    assertEquals(Order.OrderType.ASK, HitbtcAdapters.readOrderType(id));
  }

  @Test
  public void testReadSymbolDoge() throws Exception {

    String id = HitbtcAdapters.createOrderId(new LimitOrder(Order.OrderType.ASK, BigDecimal.ONE, CurrencyPair.DOGE_BTC, null, null, BigDecimal.ONE), (long) 0);

    assertEquals("DOGEBTC", HitbtcAdapters.readSymbol(id));
    assertEquals(Order.OrderType.ASK, HitbtcAdapters.readOrderType(id));
  }
}
