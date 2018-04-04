package org.knowm.xchange.therock.service.trade;

import java.io.IOException;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.therock.TheRock;
import org.knowm.xchange.therock.dto.trade.TheRockOrder;
import org.knowm.xchange.therock.dto.trade.TheRockOrder.Side;
import org.knowm.xchange.therock.dto.trade.TheRockOrder.Type;
import org.knowm.xchange.therock.dto.trade.TheRockOrders;
import org.knowm.xchange.therock.service.TheRockTradeServiceRaw;

/** Remove abstract modifier and read parent class notes in order to run the integration test */
public abstract class TheRockTradeServiceRawIntegration
    extends AbstractTheRockTradeServiceIntegration {

  private static TheRockTradeServiceRaw createUnit() {
    return new TheRockTradeServiceRaw(createExchange());
  }

  @Test
  public void testGetOrders() throws IOException {
    TheRockTradeServiceRaw unit = createUnit();
    TheRockOrders result = unit.getTheRockOrders(CurrencyPair.BTC_EUR);
    assert result.getOrders() != null;
    assert result.getMeta() != null;
  }

  @Test
  public void testSuccessfulLifecycle() throws IOException {
    // create
    TheRockTradeServiceRaw unit = createUnit();
    BigDecimal amount = new BigDecimal("0.01");
    BigDecimal price = new BigDecimal("50.0");
    TheRock.Pair pair = new TheRock.Pair(CurrencyPair.BTC_EUR);
    TheRockOrder order = new TheRockOrder(pair, Side.buy, Type.limit, amount, price);
    TheRockOrder result = unit.placeTheRockOrder(CurrencyPair.BTC_EUR, order);
    assert result.getId() != null;
    // get
    result = unit.showTheRockOrder(CurrencyPair.BTC_EUR, result.getId());
    assert result.getId() != null;
    // cancel
    unit.cancelTheRockOrder(CurrencyPair.BTC_EUR, result.getId());
  }
}
