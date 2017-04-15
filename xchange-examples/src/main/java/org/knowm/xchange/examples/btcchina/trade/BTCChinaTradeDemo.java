package org.knowm.xchange.examples.btcchina.trade;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcchina.dto.BTCChinaResponse;
import org.knowm.xchange.btcchina.dto.trade.BTCChinaOrder;
import org.knowm.xchange.btcchina.dto.trade.BTCChinaOrders;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaBooleanResponse;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaIntegerResponse;
import org.knowm.xchange.btcchina.service.rest.BTCChinaTradeServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.btcchina.BTCChinaExamplesUtils;
import org.knowm.xchange.service.trade.TradeService;

/**
 * Example showing the following:
 * <p>
 * <ul>
 * <li>Connect to BTCChina exchange with authentication</li>
 * <li>Enter, review and cancel limit orders</li>
 * </ul>
 */
public class BTCChinaTradeDemo {

  static Exchange btcchina = BTCChinaExamplesUtils.getExchange();
  static TradeService tradeService = btcchina.getTradeService();

  public static void main(String[] args) throws IOException, InterruptedException {

    generic();
    raw();
  }

  public static void generic() throws IOException, InterruptedException {

    printOpenOrders();

    // place a limit buy order
    LimitOrder limitOrder = new LimitOrder((OrderType.BID), BigDecimal.ONE, CurrencyPair.BTC_CNY, "", null, new BigDecimal("0.01"));
    String limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    Thread.sleep(1500);
    OpenOrders openOrders = printOpenOrders();

    long result = -1;
    for (LimitOrder order : openOrders.getOpenOrders()) {
      long orderId = Long.parseLong(order.getId());
      if (order.getType().equals(limitOrder.getType().toString()) && order.getLimitPrice().compareTo(limitOrder.getLimitPrice()) == 0
          && orderId > result) {

        result = orderId;
      }
    }

    // Cancel the added order
    boolean cancelResult = tradeService.cancelOrder(Long.toString(result));
    System.out.println("Canceling returned " + cancelResult);

    printOpenOrders();
  }

  private static OpenOrders printOpenOrders() throws IOException {

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders.toString());
    return openOrders;
  }

  public static void raw() throws IOException, InterruptedException {

    printOpenOrdersRaw();

    // place a limit buy order
    LimitOrder limitOrder = new LimitOrder((OrderType.BID), BigDecimal.ONE, CurrencyPair.BTC_CNY, "", null, new BigDecimal("0.01"));
    BTCChinaIntegerResponse limitOrderReturnValue = ((BTCChinaTradeServiceRaw) tradeService).buy(new BigDecimal("0.01"), BigDecimal.ONE, "BTCCNY");
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    Thread.sleep(1500);
    BTCChinaResponse<BTCChinaOrders> openOrders = printOpenOrdersRaw();

    long result = -1;
    for (int i = 0; i < openOrders.getResult().getOrdersArray().length; i++) {
      BTCChinaOrder order = openOrders.getResult().getOrdersArray()[i];
      int orderId = order.getId();
      if (order.getType().equals(limitOrder.getType().toString()) && order.getPrice().compareTo(limitOrder.getLimitPrice()) == 0
          && orderId > result) {
        result = orderId;
      }
    }

    // Cancel the added order
    BTCChinaBooleanResponse cancelResult = ((BTCChinaTradeServiceRaw) tradeService).cancelBTCChinaOrder(limitOrderReturnValue.getResult().intValue());
    System.out.println("Canceling returned " + cancelResult);

    printOpenOrders();
  }

  private static BTCChinaResponse<BTCChinaOrders> printOpenOrdersRaw() throws IOException {

    BTCChinaResponse<BTCChinaOrders> openOrders = ((BTCChinaTradeServiceRaw) tradeService).getBTCChinaOrders(true, "BTCCNY", null, null);
    System.out.println(openOrders.toString());
    return openOrders;
  }
}
