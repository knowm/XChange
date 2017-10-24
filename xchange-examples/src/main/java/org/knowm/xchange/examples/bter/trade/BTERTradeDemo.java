package org.knowm.xchange.examples.bter.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bter.dto.BTEROrderType;
import org.knowm.xchange.bter.dto.trade.BTEROpenOrder;
import org.knowm.xchange.bter.dto.trade.BTEROpenOrders;
import org.knowm.xchange.bter.dto.trade.BTEROrderStatus;
import org.knowm.xchange.bter.dto.trade.BTERTrade;
import org.knowm.xchange.bter.service.BTERTradeServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.bter.BTERDemoUtils;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamCurrencyPair;

public class BTERTradeDemo {

  public static void main(String[] args) throws IOException, InterruptedException {

    Exchange exchange = BTERDemoUtils.createExchange();
    TradeService tradeService = exchange.getTradeService();

    generic(tradeService);
    raw((BTERTradeServiceRaw) tradeService);
  }

  private static void generic(TradeService tradeService) throws IOException, InterruptedException {

    LimitOrder limitOrder = new LimitOrder(OrderType.ASK, new BigDecimal("0.384"), CurrencyPair.LTC_BTC, "", null, new BigDecimal("0.0265"));
    String orderId = tradeService.placeLimitOrder(limitOrder);
    System.out.println(orderId); // Returned order id is currently broken for BTER, rely on open orders instead for demo :(

    Thread.sleep(2000); // wait for BTER's back-end to propagate the order

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders);

    List<LimitOrder> openOrdersList = openOrders.getOpenOrders();
    if (!openOrdersList.isEmpty()) {
      String existingOrderId = openOrdersList.get(0).getId();

      boolean isCancelled = tradeService.cancelOrder(existingOrderId);
      System.out.println(isCancelled);
    }

    Thread.sleep(2000); // wait for BTER's back-end to propagate the cancelled order

    openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders);

    Thread.sleep(2000);

    Trades tradeHistory = tradeService.getTradeHistory(new DefaultTradeHistoryParamCurrencyPair(CurrencyPair.LTC_BTC));
    System.out.println(tradeHistory);
  }

  private static void raw(BTERTradeServiceRaw tradeService) throws IOException, InterruptedException {

    String placedOrderId = tradeService.placeBTERLimitOrder(CurrencyPair.LTC_BTC, BTEROrderType.SELL, new BigDecimal("0.0265"),
        new BigDecimal("0.384"));
    System.out.println(placedOrderId);

    Thread.sleep(2000); // wait for BTER's back-end to propagate the order

    BTEROpenOrders openOrders = tradeService.getBTEROpenOrders();
    System.out.println(openOrders);

    List<BTEROpenOrder> openOrdersList = openOrders.getOrders();
    if (!openOrdersList.isEmpty()) {
      String existingOrderId = openOrdersList.get(0).getId();
      BTEROrderStatus orderStatus = tradeService.getBTEROrderStatus(existingOrderId);
      System.out.println(orderStatus);

      boolean isCancelled = tradeService.cancelOrder(existingOrderId);
      System.out.println(isCancelled);
    }

    Thread.sleep(2000); // wait for BTER's back-end to propagate the cancelled order

    openOrders = tradeService.getBTEROpenOrders();
    System.out.println(openOrders);

    List<BTERTrade> tradeHistory = tradeService.getBTERTradeHistory(CurrencyPair.LTC_BTC).getTrades();
    System.out.println(tradeHistory);

  }
}
