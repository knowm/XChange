package com.xeiam.xchange.hitbtc.service.polling;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class HitbtcTradeServiceRawTest {


  @Test
  public void testReadSymbol() throws Exception {

    String id = HitbtcTradeServiceRawPublic.createId(new LimitOrder(Order.OrderType.ASK, BigDecimal.ONE, CurrencyPair.BTC_EUR, null, null, BigDecimal.ONE), 0);
    assertEquals("BTCEUR", HitbtcTradeServiceRawPublic.readSymbol(id));
    assertEquals(Order.OrderType.ASK, HitbtcTradeServiceRawPublic.readOrderType(id));
  }

  @Test
  public void testReadSymbolDoge() throws Exception {

    String id = HitbtcTradeServiceRawPublic.createId(new LimitOrder(Order.OrderType.ASK, BigDecimal.ONE, CurrencyPair.DOGE_BTC, null, null, BigDecimal.ONE), 0);
    assertEquals("DOGEBTC", HitbtcTradeServiceRawPublic.readSymbol(id));
    assertEquals(Order.OrderType.ASK, HitbtcTradeServiceRawPublic.readOrderType(id));
  }
}

class HitbtcTradeServiceRawPublic extends HitbtcTradeServiceRaw {

  private HitbtcTradeServiceRawPublic() {
    super(null, null);
  }

  public static String createSymbol(CurrencyPair pair) {

    return HitbtcTradeServiceRaw.createSymbol(pair);
  }

  public static String createId(Order order, long nonce) {

    return HitbtcTradeServiceRaw.createId(order, nonce);
  }

  public static Order.OrderType readOrderType(String orderId) {

    return HitbtcTradeServiceRaw.readOrderType(orderId);
  }

  protected static String readSymbol(String orderId) {

    return HitbtcTradeServiceRaw.readSymbol(orderId);
  }

}

