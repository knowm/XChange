package com.xeiam.xchange.gatecoin.testclient.trade;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.gatecoin.dto.trade.GatecoinOrder;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.gatecoin.dto.trade.Results.GatecoinCancelOrderResult;
import com.xeiam.xchange.gatecoin.dto.trade.Results.GatecoinOrderResult;
import com.xeiam.xchange.gatecoin.dto.trade.Results.GatecoinPlaceOrderResult;
import com.xeiam.xchange.gatecoin.service.polling.GatecoinTradeServiceRaw;
import com.xeiam.xchange.gatecoin.testclient.GatecoinDemoUtils;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;


public class GatecoinOrderDemo {

  public static void main(String[] args) throws IOException {
      
    Exchange gatecoin = GatecoinDemoUtils.createExchange();
    PollingTradeService tradeService = gatecoin.getPollingTradeService();

    generic(tradeService);
    raw((GatecoinTradeServiceRaw) tradeService);
  }

  private static void generic(PollingTradeService tradeService) throws IOException {
  
    // place a limit buy order
    LimitOrder limitOrder = new LimitOrder((OrderType.ASK), new BigDecimal("2"), CurrencyPair.BTC_USD, "", null, new BigDecimal("1000.00"));
    String limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order return value: " + limitOrderReturnValue);
    
     // place a market buy order
    MarketOrder marketOrder = new MarketOrder((OrderType.ASK), new BigDecimal("1"), CurrencyPair.BTC_HKD);
    String marketOrderReturnValue = tradeService.placeMarketOrder(marketOrder);
    System.out.println("Market Order return value: " + marketOrderReturnValue);

    printOpenOrders(tradeService);

    // Cancel the added order
    boolean cancelResult = tradeService.cancelOrder(limitOrderReturnValue);
    System.out.println("Canceling returned " + cancelResult);

  }

  private static void printOpenOrders(PollingTradeService tradeService) throws IOException {

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println("Open Orders: " + openOrders.toString());
  }

  private static void raw(GatecoinTradeServiceRaw tradeService) throws IOException {

   // printRawOpenOrders(tradeService);

    // place a limit buy order
    GatecoinPlaceOrderResult order = tradeService.placeGatecoinOrder(new BigDecimal(".001"), new BigDecimal("1000.00"), "BID", "BTCUSD");
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
