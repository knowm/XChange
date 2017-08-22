package org.knowm.xchange.therock.service.trade;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.therock.service.TheRockOpenOrdersParams;
import org.knowm.xchange.therock.service.TheRockTradeService;
import org.knowm.xchange.therock.service.TheRockTradeServiceRaw;

/**
 * Remove abstract modifier and read parent class notes in order to run the integration test
 */
public abstract class TheRockTradeServiceIntegration extends AbstractTheRockTradeServiceIntegration {

  TheRockTradeServiceRaw unit = createUnit();

  private static TheRockTradeService createUnit() {
    return new TheRockTradeService(createExchange());
  }

  @Test
  public void testPlaceLimitOrder() throws IOException {
    TheRockTradeService unit = createUnit();
    BigDecimal amount = new BigDecimal("0.01");
    BigDecimal price = new BigDecimal("50.0");
    LimitOrder limitOrder = new LimitOrder(OrderType.BID, amount, CurrencyPair.BTC_EUR, null, null, price);
    String id = unit.placeLimitOrder(limitOrder);
    assert id != null;
  }

  @Test
  public void testOpenOrders() throws IOException {
    TheRockTradeService unit = createUnit();

    TheRockOpenOrdersParams openOrdersParams = new TheRockOpenOrdersParams();
    openOrdersParams.setCurrencyPair(CurrencyPair.BTC_EUR);

    OpenOrders openOrders = unit.getOpenOrders(openOrdersParams);
    assert openOrders != null;
  }

}
