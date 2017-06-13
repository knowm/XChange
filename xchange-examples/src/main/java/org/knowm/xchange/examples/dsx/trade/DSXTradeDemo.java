package org.knowm.xchange.examples.dsx.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dsx.dto.trade.DSXCancelOrderResult;
import org.knowm.xchange.dsx.dto.trade.DSXOrder;
import org.knowm.xchange.dsx.dto.trade.DSXTradeResult;
import org.knowm.xchange.dsx.service.DSXTradeServiceRaw;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.dsx.DSXExamplesUtils;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.TradeService;
/**
 * @author Mikhail Wall
 */
public class DSXTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange dsx = DSXExamplesUtils.createExchange();
    //generic(dsx);
    raw(dsx);
  }

  private static void generic(Exchange exchange) throws IOException {

    TradeService tradeService = exchange.getTradeService();

    printOpenOrders(tradeService);

    LimitOrder limitOrder = new LimitOrder(Order.OrderType.ASK, new BigDecimal("0.1"), CurrencyPair.BTC_USD, "", new Date(), new BigDecimal("1600.64"));

    String limitOrderReturnValue = null;
    try {
      limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);
      System.out.println("Limit Order return value: " + limitOrderReturnValue);

      printOpenOrders(tradeService);

      boolean cancelResult = tradeService.cancelOrder(limitOrderReturnValue);
      System.out.println("Canceling returned " + cancelResult);
    } catch (ExchangeException e) {
      System.out.println(e.getMessage());
    }

    printOpenOrders(tradeService);
  }

  private static void raw(Exchange exchange) throws IOException {
    DSXTradeServiceRaw tradeService = (DSXTradeServiceRaw) exchange.getTradeService();

    tradeService.getFees();
    printRawOpenOrders(tradeService);

    // place buy order
    DSXOrder.Type type = DSXOrder.Type.buy;
    String pair = "btcusd";
    DSXOrder dsxOrder = new DSXOrder(pair, type, new BigDecimal("0.1"), new BigDecimal("1600"), new Date().getTime(), 0, DSXOrder.OrderType.limit);

    DSXTradeResult result = null;
    try {
      result = tradeService.tradeDSX(dsxOrder);
      System.out.println("tradeDSX return value:" + result);

      printRawOpenOrders(tradeService);

      DSXCancelOrderResult cancelResult = tradeService.cancelDSXOrder(result.getOrderId());
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

  private static void printRawOpenOrders(DSXTradeServiceRaw tradeService) throws IOException {

    Map<Long, DSXOrder> openOrders = tradeService.getDSXActiveOrders(null);
    for (Map.Entry<Long, DSXOrder> entry : openOrders.entrySet()) {
      System.out.println("ID: " + entry.getKey() + ", Order:" + entry.getValue());
    }
  }
}
