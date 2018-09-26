package org.knowm.xchange.test.getbtc;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.getbtc.dto.params.GetbtcCancelOrderByCurrencyPair;
import org.knowm.xchange.getbtc.dto.params.GetbtcOpenOrdersParams;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

/**
 * kevinobamatheus@gmail.com
 *
 * @author kevingates
 */
public class TradeServiceIntegration {

  public static void main(String[] args) {
    try {
      String category = "getOpenOrders";

      switch (category) {
        case "getOpenOrders":
          getOpenOrders();
          break;
        case "placeLimitOrder":
          placeLimitOrder();
          break;
        case "cancelOrder":
          cancelOrder();
          break;
        default:
          getOpenOrders();
          break;
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** @throws IOException */
  private static void getOpenOrders() throws IOException {
    Exchange exx = GetbtcConfig.getExchange();

    TradeService tradeService = exx.getTradeService();

    try {
      GetbtcOpenOrdersParams exxOpenOrdersParams =
          (GetbtcOpenOrdersParams) tradeService.createOpenOrdersParams();
      exxOpenOrdersParams.setCurrencyPair(CurrencyPair.ETP_BTC);
      exxOpenOrdersParams.setType("buy");

      printOpenOrders(tradeService, exxOpenOrdersParams);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void printOpenOrders(TradeService tradeService, OpenOrdersParams openOrdersParams)
      throws IOException {
    OpenOrders openOrders = tradeService.getOpenOrders(openOrdersParams);
    System.out.printf("Open Orders for %s: %s%n", openOrdersParams, openOrders);
  }

  private static void placeLimitOrder() throws IOException {
    Exchange exx = GetbtcConfig.getExchange();

    TradeService tradeService = exx.getTradeService();

    try {
      // place a limit order
      LimitOrder limitOrder =
          new LimitOrder(
              (OrderType.BID),
              new BigDecimal("1.0"),
              CurrencyPair.ETP_BTC,
              null,
              null,
              new BigDecimal("0.00050"));

      String limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);
      System.out.println("Order Id: " + limitOrderReturnValue);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void cancelOrder() throws IOException {
    Exchange exx = GetbtcConfig.getExchange();
    TradeService tradeService = exx.getTradeService();
    try {
      GetbtcCancelOrderByCurrencyPair exxCancelOrderByCurrencyPair =
          new GetbtcCancelOrderByCurrencyPair();
      exxCancelOrderByCurrencyPair.setCurrencyPair(CurrencyPair.ETP_BTC);
      exxCancelOrderByCurrencyPair.setId("634");

      boolean result = tradeService.cancelOrder(exxCancelOrderByCurrencyPair);
      System.out.println("cancelOrder result: " + result);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
