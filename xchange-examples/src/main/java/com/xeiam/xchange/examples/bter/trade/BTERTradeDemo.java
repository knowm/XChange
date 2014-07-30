package com.xeiam.xchange.examples.bter.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bter.dto.BTEROrderType;
import com.xeiam.xchange.bter.dto.trade.BTEROpenOrder;
import com.xeiam.xchange.bter.dto.trade.BTEROpenOrders;
import com.xeiam.xchange.bter.dto.trade.BTEROrderStatus;
import com.xeiam.xchange.bter.dto.trade.BTERTrade;
import com.xeiam.xchange.bter.service.polling.BTERPollingTradeServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.examples.bter.BTERDemoUtils;
import com.xeiam.xchange.service.polling.PollingTradeService;

public class BTERTradeDemo {

  public static void main(String[] args) throws IOException, InterruptedException {

    Exchange exchange = BTERDemoUtils.createExchange();
    PollingTradeService tradeService = exchange.getPollingTradeService();

    generic(tradeService);
    raw((BTERPollingTradeServiceRaw) tradeService);
  }

  private static void generic(PollingTradeService tradeService) throws IOException, InterruptedException {

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

    Trades tradeHistory = tradeService.getTradeHistory(CurrencyPair.LTC_BTC);
    System.out.println(tradeHistory);
  }

  private static void raw(BTERPollingTradeServiceRaw tradeService) throws IOException, InterruptedException {

    boolean placedOrderResult = tradeService.placeBTERLimitOrder(CurrencyPair.LTC_BTC, BTEROrderType.SELL, new BigDecimal("0.0265"), new BigDecimal("0.384"));
    System.out.println(placedOrderResult); // Returned order id is currently broken for BTER, rely on open orders instead for demo :(

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
