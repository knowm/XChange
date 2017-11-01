package org.knowm.xchange.hitbtc.service.trade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.hitbtc.AuthenticatedBaseTestCase;
import org.knowm.xchange.hitbtc.Hitbtc;
import org.knowm.xchange.hitbtc.HitbtcAdapters;
import org.knowm.xchange.hitbtc.HitbtcExchange;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcExecutionReport;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcExecutionReportResponse;
import org.knowm.xchange.hitbtc.service.HitbtcTradeServiceRaw;

public class HitbtcTradeServiceRawTest extends AuthenticatedBaseTestCase {

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

  @Ignore //Ignored because this executes a real trade.
  @Test
  public void testCreateAndCancelOrder() throws Exception {

    LimitOrder limitOrder = new LimitOrder(Order.OrderType.BID, new BigDecimal("0.01"), CurrencyPair.BTC_USD, null, null, new BigDecimal("1200.00"));

    HitbtcTradeServiceRaw hitbtcTradeServiceRaw = (HitbtcTradeServiceRaw) EXCHANGE.getTradeService();

    HitbtcExecutionReport hitbtcExecutionReport = hitbtcTradeServiceRaw.placeLimitOrderRaw(limitOrder);
    assertNotNull(hitbtcExecutionReport);

    HitbtcExecutionReportResponse hitbtcExecutionReportResponse = hitbtcTradeServiceRaw.cancelOrderRaw(hitbtcExecutionReport.getClientOrderId());
    assertNotNull(hitbtcExecutionReportResponse);
    assertEquals(hitbtcExecutionReportResponse.getExecutionReport().getOrderStatus(), "canceled");
  }

}
