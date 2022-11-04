package org.knowm.xchange.bl3p.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bl3p.Bl3pExchange;
import org.knowm.xchange.bl3p.service.params.Bl3pTradeHistoryParams;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;

public class Bl3pTradeServiceIntegration {

  Exchange exchange = ExchangeFactory.INSTANCE.createExchange(Bl3pExchange.class);
  TradeService tradeService = exchange.getTradeService();

  @Test
  public void getOpenOrders() throws IOException {
    /*
    OpenOrders openOrders = tradeService.getOpenOrders();

    System.out.println(openOrders);
    */
  }

  @Test
  public void createMarketOrder() throws IOException {
    /**
     * MarketOrder order = new MarketOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_EUR)
     * .originalAmount(new BigDecimal("0.0001")) .build();
     *
     * <p>String orderId = tradeService.placeMarketOrder(order);
     *
     * <p>System.out.println(orderId);
     */
  }

  @Ignore("HTTP status code was not OK: 400")
  @Test
  public void createLimitOrder() throws IOException {
    LimitOrder order =
        new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_EUR)
            .originalAmount(new BigDecimal("0.001"))
            .limitPrice(new BigDecimal("4000"))
            .build();

    String orderId = tradeService.placeLimitOrder(order);

    System.out.println(orderId);
  }

  @Test
  public void cancelOrder() throws IOException {
    /*
    CancelOrderByIdAndCurrencyPairParams params =
        new CancelOrderByIdAndCurrencyPairParams(CurrencyPair.BTC_EUR, "42467560");
    boolean result = tradeService.cancelOrder(params);

    System.out.println(result);
    */
  }

  @Test
  public void getOrder() throws IOException {
    /*
    OrderQueryParams p =
        new Bl3pTradeService.Bl3pOrderQueryParams(CurrencyPair.BTC_EUR, "42409168");
    Collection<Order> result = this.tradeService.getOrder(p);

    System.out.println(result.toArray()[0]);
    */
  }

  @Ignore("HTTP status code was not OK: 400")
  @Test
  public void getTradeHistory() throws IOException {
    Bl3pTradeHistoryParams p =
        new Bl3pTradeHistoryParams(Currency.BTC, Bl3pTradeHistoryParams.TransactionType.TRADE);
    UserTrades trades = this.tradeService.getTradeHistory(p);

    System.out.println(trades);
  }
}
