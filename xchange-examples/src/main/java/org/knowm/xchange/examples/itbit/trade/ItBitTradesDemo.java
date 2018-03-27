package org.knowm.xchange.examples.itbit.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.itbit.ItBitDemoUtils;
import org.knowm.xchange.itbit.v1.dto.trade.ItBitOrder;
import org.knowm.xchange.itbit.v1.service.ItBitTradeServiceRaw;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.TradeService;

public class ItBitTradesDemo {

  public static void main(String[] args) throws Exception {

    Exchange itbit = ItBitDemoUtils.createExchange();

    AccountService account = itbit.getAccountService();
    TradeService tradeService = itbit.getTradeService();

    AccountInfo accountInfo = account.getAccountInfo();
    System.out.println("Account Info: " + accountInfo);

    printOpenOrders(tradeService);

    String placeLimitOrderXBT =
        tradeService.placeLimitOrder(
            new LimitOrder(
                OrderType.BID,
                BigDecimal.valueOf(0.001),
                new CurrencyPair("XBT", "USD"),
                "0",
                new Date(),
                BigDecimal.valueOf(300)));
    String placeLimitOrderBTC =
        tradeService.placeLimitOrder(
            new LimitOrder(
                OrderType.BID,
                BigDecimal.valueOf(0.001),
                new CurrencyPair("BTC", "USD"),
                "0",
                new Date(),
                BigDecimal.valueOf(360)));

    System.out.println("limit order id " + placeLimitOrderXBT);
    System.out.println("limit order id " + placeLimitOrderBTC);
    printOrderStatus(tradeService, placeLimitOrderXBT);
    printOrderStatus(tradeService, placeLimitOrderBTC);
    printOpenOrders(tradeService);

    System.out.println("Cancelling " + placeLimitOrderXBT);
    tradeService.cancelOrder(placeLimitOrderXBT);
    printOrderStatus(tradeService, placeLimitOrderXBT);
    printOpenOrders(tradeService);

    System.out.println("Cancelling " + placeLimitOrderBTC);
    tradeService.cancelOrder(placeLimitOrderBTC);
    printOrderStatus(tradeService, placeLimitOrderBTC);
    printOpenOrders(tradeService);

    Trades tradeHistory = tradeService.getTradeHistory(tradeService.createTradeHistoryParams());
    System.out.println("Trade history: " + tradeHistory);

    printOrderStatus(tradeService, placeLimitOrderXBT);
    printOrderStatus(tradeService, placeLimitOrderBTC);
    printOpenOrders(tradeService);
  }

  private static void printOpenOrders(TradeService tradeService) throws IOException {

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println("Open Orders: " + openOrders.toString());
  }

  private static void printOrderStatus(TradeService tradeService, String orderId)
      throws IOException {

    final ItBitTradeServiceRaw tradeServiceRaw = (ItBitTradeServiceRaw) tradeService;
    final ItBitOrder response = tradeServiceRaw.getItBitOrder(orderId);
    System.out.println("Order Status: " + response.toString());
  }
}
