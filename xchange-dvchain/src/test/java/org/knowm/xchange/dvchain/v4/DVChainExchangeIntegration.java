package org.knowm.xchange.dvchain.v4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.dvchain.DVChainExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsZero;

public class DVChainExchangeIntegration {
  final String secret =
      "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVjMGZkZjczNTUxYTU4M2I2OGNmOTM1YSIsImlhdCI6MTU0NDcyNjk3Nn0.wBuyED4CmkFzzrNdQm1FqwixJhvQTfl-aN4OE0ryoho";

  @Test
  public void shouldBeInstantiatedWithoutAnExceptionWhenUsingDefaultSpecification() {
    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(DVChainExchange.class.getCanonicalName());
    ExchangeSpecification exchangeSpecification = exchange.getExchangeSpecification();
    exchangeSpecification.setSslUri("https://sandbox.trade.dvchain.co");
    exchangeSpecification.setHost("sandbox.trade.dvchain.co");
    exchange.applySpecification(exchangeSpecification);
  }

  @Test
  public void testExchangeMarketData() {
    final Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(DVChainExchange.class, secret, secret);
    ExchangeSpecification exchangeSpecification = exchange.getExchangeSpecification();
    exchangeSpecification.setSslUri("https://sandbox.trade.dvchain.co");
    exchangeSpecification.setHost("sandbox.trade.dvchain.co");
    exchange.applySpecification(exchangeSpecification);
    final MarketDataService marketDataService = exchange.getMarketDataService();
    try {
      OrderBook orderBook = marketDataService.getOrderBook(new CurrencyPair("BTC", "USD"));
      List<LimitOrder> bids = orderBook.getBids();
      assertEquals(bids.size(), 3);
      List<LimitOrder> asks = orderBook.getAsks();
      assertEquals(asks.size(), 3);
      System.out.println(orderBook.toString());
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
      assert (false);
    }
  }

  @Test
  public void testMarketOrder() {

    final Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(DVChainExchange.class, secret, secret);
    ExchangeSpecification exchangeSpecification = exchange.getExchangeSpecification();
    exchangeSpecification.setSslUri("https://sandbox.trade.dvchain.co");
    exchangeSpecification.setHost("sandbox.trade.dvchain.co");
    exchange.applySpecification(exchangeSpecification);
    TradeService tradeService = exchange.getTradeService();
    try {
      String order =
          tradeService.placeMarketOrder(
              new MarketOrder(
                  Order.OrderType.BID, new BigDecimal(1), new CurrencyPair("BTC", "USD")));
      assertNotNull(order);
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
      assert (false);
    }
  }

  @Test
  public void testLimitOrder() {
    final Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(DVChainExchange.class, secret, secret);
    ExchangeSpecification exchangeSpecification = exchange.getExchangeSpecification();
    exchangeSpecification.setSslUri("https://sandbox.trade.dvchain.co");
    exchangeSpecification.setHost("sandbox.trade.dvchain.co");
    exchange.applySpecification(exchangeSpecification);
    TradeService tradeService = exchange.getTradeService();
    try {
      String order =
          tradeService.placeLimitOrder(
              new LimitOrder(
                  Order.OrderType.ASK,
                  new BigDecimal(1),
                  new CurrencyPair("BTC", "USD"),
                  "",
                  null,
                  new BigDecimal("7001")));
      assertNotNull(order);
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
      assert (false);
    }
  }

  @Test
  public void testOrders() {
    final Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(DVChainExchange.class, secret, secret);
    ExchangeSpecification exchangeSpecification = exchange.getExchangeSpecification();
    exchangeSpecification.setSslUri("https://sandbox.trade.dvchain.co");
    exchangeSpecification.setHost("sandbox.trade.dvchain.co");
    exchange.applySpecification(exchangeSpecification);
    TradeService tradeService = exchange.getTradeService();
    try {
      OpenOrders orders = tradeService.getOpenOrders();
      assertNotNull(orders);
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
      assert (false);
    }
  }

  @Test
  public void testTrades() {
    final Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(DVChainExchange.class, secret, secret);
    ExchangeSpecification exchangeSpecification = exchange.getExchangeSpecification();
    exchangeSpecification.setSslUri("https://sandbox.trade.dvchain.co");
    exchangeSpecification.setHost("sandbox.trade.dvchain.co");
    exchange.applySpecification(exchangeSpecification);
    TradeService tradeService = exchange.getTradeService();
    try {
      UserTrades trades = tradeService.getTradeHistory(new TradeHistoryParamsZero());
      assertNotNull(trades);
    } catch (Exception exception) {
      System.out.println(exception.getMessage());
      assert (false);
    }
  }
}
