package org.knowm.xchange.examples.gatecoin.trade;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.gatecoin.GatecoinDemoUtils;
import org.knowm.xchange.gatecoin.dto.trade.GatecoinOrder;
import org.knowm.xchange.gatecoin.dto.trade.Results.GatecoinCancelOrderResult;
import org.knowm.xchange.gatecoin.dto.trade.Results.GatecoinOrderResult;
import org.knowm.xchange.gatecoin.dto.trade.Results.GatecoinPlaceOrderResult;
import org.knowm.xchange.gatecoin.service.GatecoinTradeServiceRaw;
import org.knowm.xchange.service.trade.TradeService;

public class GatecoinOrderDemo {

  public static void main(String[] args) throws IOException {

    Exchange gatecoin = GatecoinDemoUtils.createExchange();
    TradeService tradeService = gatecoin.getTradeService();

    generic(tradeService);
    raw((GatecoinTradeServiceRaw) tradeService);
  }

  private static void generic(TradeService tradeService) throws IOException {

    // place a limit buy order
    LimitOrder limitOrder =
        new LimitOrder(
            (OrderType.ASK),
            new BigDecimal("2"),
            CurrencyPair.BTC_USD,
            "",
            null,
            new BigDecimal("1000.00"));
    String limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    // place a market buy order
    MarketOrder marketOrder =
        new MarketOrder((OrderType.ASK), new BigDecimal("1"), CurrencyPair.BTC_HKD);
    String marketOrderReturnValue = tradeService.placeMarketOrder(marketOrder);
    System.out.println("Market Order return value: " + marketOrderReturnValue);

    printOpenOrders(tradeService);

    // Cancel the added order
    boolean cancelResult = tradeService.cancelOrder(limitOrderReturnValue);
    System.out.println("Canceling returned " + cancelResult);
  }

  private static void printOpenOrders(TradeService tradeService) throws IOException {

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println("Open Orders: " + openOrders.toString());
  }

  private static void raw(GatecoinTradeServiceRaw tradeService) throws IOException {

    // printRawOpenOrders(tradeService);

    // place a limit buy order
    GatecoinPlaceOrderResult order =
        tradeService.placeGatecoinOrder(
            new BigDecimal(".001"), new BigDecimal("1000.00"), "BID", "BTCUSD");
    System.out.println("GatecoinOrder return value: " + order.getResponseStatus().getMessage());

    printRawOpenOrders(tradeService);

    // Cancel the added order
    GatecoinCancelOrderResult cancelResult = tradeService.cancelGatecoinOrder(order.getOrderId());
    System.out.println("Canceling returned " + cancelResult.getResponseStatus().getMessage());

    printRawOpenOrders(tradeService);
  }

  private static void printRawOpenOrders(GatecoinTradeServiceRaw tradeService) throws IOException {

    GatecoinOrderResult openOrdersResult = tradeService.getGatecoinOpenOrders();
    System.out.println("Open Orders: " + openOrdersResult.getOrders().length);
    for (GatecoinOrder order : openOrdersResult.getOrders()) {
      System.out.println(order.toString());
    }
  }
}
