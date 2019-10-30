package org.knowm.xchange.latoken.dto.trade;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.latoken.LatokenExchange;
import org.knowm.xchange.latoken.service.LatokenTradeService;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TradeServiceIntegration {

  static Logger LOG = LoggerFactory.getLogger(TradeServiceIntegration.class);

  static Exchange exchange;
  static LatokenTradeService tradeService;

  @BeforeClass
  public static void beforeClass() {
    exchange =
        ExchangeFactory.INSTANCE.createExchange(
            LatokenExchange.class.getName(), "api-v1-XXX", "api-v1-secret-YYY");
    tradeService = (LatokenTradeService) exchange.getTradeService();
  }

  @Before
  public void before() {
    Assume.assumeNotNull(exchange.getExchangeSpecification().getApiKey());
  }

  @Test
  public void openOrders() throws Exception {

    DefaultOpenOrdersParamCurrencyPair params =
        (DefaultOpenOrdersParamCurrencyPair) tradeService.createOpenOrdersParams();
    params.setCurrencyPair(CurrencyPair.ETH_BTC);
    List<LimitOrder> orders = tradeService.getOpenOrders(params).getOpenOrders();
    orders.forEach(order -> System.out.println(order));
  }

  @Test
  public void newOrder() throws Exception {

    CurrencyPair pair = CurrencyPair.ETH_BTC;
    OrderType type = OrderType.BID;
    BigDecimal amount = BigDecimal.valueOf(0.01);
    BigDecimal limitPrice = BigDecimal.valueOf(0.018881);
    LimitOrder newOrder =
        new LimitOrder.Builder(type, pair)
            .originalAmount(amount)
            .limitPrice(limitPrice)
            .timestamp(new Date(System.currentTimeMillis()))
            .build();

    // Test order
    LatokenTestOrder testOrder =
        tradeService.placeLatokenTestOrder(pair, "", LatokenOrderSide.buy, limitPrice, amount);
    System.out.println(testOrder);

    // Place order
    String newOrderId = tradeService.placeLimitOrder(newOrder);
    System.out.println(newOrderId);

    // Check open orders
    DefaultOpenOrdersParamCurrencyPair params =
        (DefaultOpenOrdersParamCurrencyPair) tradeService.createOpenOrdersParams();
    params.setCurrencyPair(CurrencyPair.ETH_BTC);
    List<LimitOrder> openOrders = tradeService.getOpenOrders(params).getOpenOrders();
    System.out.println(openOrders);

    // Cancel
    tradeService.cancelLatokenOrder(newOrderId);

    // Check open orders
    openOrders = tradeService.getOpenOrders().getOpenOrders();
    System.out.println(openOrders);
  }
}
