package org.knowm.xchange.examples.btce.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btce.v3.dto.trade.BTCECancelOrderResult;
import org.knowm.xchange.btce.v3.dto.trade.BTCEOrder;
import org.knowm.xchange.btce.v3.dto.trade.BTCEPlaceOrderResult;
import org.knowm.xchange.btce.v3.service.BTCETradeServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.btce.BTCEExamplesUtils;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.TradeService;

/**
 * @author Matija Mazi
 */
public class BTCETradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange btce = BTCEExamplesUtils.createExchange();
    generic(btce);
    raw(btce);
  }

  private static void generic(Exchange exchange) throws IOException {

    TradeService tradeService = exchange.getTradeService();

    printOpenOrders(tradeService);

    // place a limit buy order
    LimitOrder limitOrder = new LimitOrder(Order.OrderType.ASK, new BigDecimal("0.1"), CurrencyPair.BTC_USD, "", null, new BigDecimal("1023.45"));

    String limitOrderReturnValue = null;
    try {
      limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);
      System.out.println("Limit Order return value: " + limitOrderReturnValue);

      printOpenOrders(tradeService);

      // Cancel the added order
      boolean cancelResult = tradeService.cancelOrder(limitOrderReturnValue);
      System.out.println("Canceling returned " + cancelResult);
    } catch (ExchangeException e) {
      System.out.println(e.getMessage());
    }

    printOpenOrders(tradeService);
  }

  private static void raw(Exchange exchange) throws IOException {

    BTCETradeServiceRaw tradeService = (BTCETradeServiceRaw) exchange.getTradeService();

    printRawOpenOrders(tradeService);

    // place buy order
    BTCEOrder.Type type = BTCEOrder.Type.buy;
    String pair = "btc_usd";
    BTCEOrder btceOrder = new BTCEOrder(0, null, new BigDecimal("1"), new BigDecimal("0.1"), type, pair);

    BTCEPlaceOrderResult result = null;
    try {
      result = tradeService.placeBTCEOrder(btceOrder);
      System.out.println("placeBTCEOrder return value: " + result);

      printRawOpenOrders(tradeService);

      // Cancel the added order
      BTCECancelOrderResult cancelResult = tradeService.cancelBTCEOrder(result.getOrderId());
      System.out.println("Canceling returned " + cancelResult);
    } catch (ExchangeException e) {
      System.out.println(e.getMessage());
    }

    printRawOpenOrders(tradeService);
  }

  private static void printOpenOrders(TradeService tradeService) throws IOException {

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println("Open Orders: " + openOrders.toString());
  }

  private static void printRawOpenOrders(BTCETradeServiceRaw tradeService) throws IOException {

    Map<Long, BTCEOrder> openOrders = tradeService.getBTCEActiveOrders(null);
    for (Map.Entry<Long, BTCEOrder> entry : openOrders.entrySet()) {
      System.out.println("ID: " + entry.getKey() + ", Order:" + entry.getValue());
    }
  }

}
