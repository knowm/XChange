package org.knowm.xchange.hitbtc.v2.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.hitbtc.v2.BaseAuthenticatedServiceTest;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test ignored in default build because it requires production authentication credentials. See {@link BaseAuthenticatedServiceTest}.
 */
@Ignore
public class HitbtcTradeServiceRawTest extends BaseAuthenticatedServiceTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(HitbtcTradeServiceRawTest.class);

  private HitbtcTradeServiceRaw service = (HitbtcTradeServiceRaw) exchange.getTradeService();

  @Rule
  public final ExpectedException exception = ExpectedException.none();

  @Test
  public void testPlaceLimitOrderRaw() throws IOException {

    Date date = new Date();
    String id = date.toString().replace(" ", "");
    LOGGER.info("Placing order id : " + id);

    BigDecimal limitPrice = new BigDecimal("1.00");

    LimitOrder limitOrder = new LimitOrder(Order.OrderType.BID, new BigDecimal("0.01"), new CurrencyPair(Currency.BTC, Currency.USD), id, new Date(), limitPrice);

    service.placeLimitOrderRaw(limitOrder);
  }


  @Test
  public void testCancelOrder_wrongOrder() throws IOException {

    exception.expect(HitbtcException.class);
    exception.expectMessage("Order not found");

    service.cancelOrderRaw("WRONG");
  }

}
