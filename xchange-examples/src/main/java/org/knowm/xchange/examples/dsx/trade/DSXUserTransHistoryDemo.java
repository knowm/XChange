package org.knowm.xchange.examples.dsx.trade;

import java.io.IOException;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dsx.dto.trade.DSXOrder;
import org.knowm.xchange.dsx.dto.trade.DSXOrderHistoryResult;
import org.knowm.xchange.dsx.dto.trade.DSXTransHistoryResult;
import org.knowm.xchange.dsx.service.DSXTradeServiceRaw;
import org.knowm.xchange.examples.dsx.DSXExamplesUtils;
import org.knowm.xchange.exceptions.ExchangeException;

/**
 * @author Mikhail Wall
 */

public class DSXUserTransHistoryDemo {

  public static void main(String[] args) throws IOException {

    Exchange dsx = DSXExamplesUtils.createExchange();
    reaw(dsx);
    raw(dsx);
    raww(dsx);
  }

  private static void raw(Exchange exchange) throws IOException {
    DSXTradeServiceRaw tradeService = (DSXTradeServiceRaw) exchange.getTradeService();
    Map<Long, DSXTransHistoryResult> trans = null;
    try {
      trans = tradeService.getDSXTransHistory(null, null, null, null, null, null, null, null, null);
      for (Map.Entry<Long, DSXTransHistoryResult> entry : trans.entrySet()) {
        System.out.println("ID: " + entry.getKey() + ", Trans:" + entry.getValue());
      }
      System.out.println(trans.toString());
    } catch (ExchangeException e) {
      System.out.println(e.getMessage());
    }
  }

  private static void raww(Exchange exchange) throws IOException {
    DSXTradeServiceRaw tradeService = (DSXTradeServiceRaw) exchange.getTradeService();
    Map<Long, DSXOrderHistoryResult> orders = null;
    try {
      orders = tradeService.getDSXOrderHistory(null, null, null, null, null, null, null);
      for (Map.Entry<Long, DSXOrderHistoryResult> entry : orders.entrySet()) {
        System.out.println("id: " + entry.getKey() + ", orders:" + entry.getValue());
      }
      System.out.println(orders.toString());
    } catch (ExchangeException e) {
      System.out.println(e.getMessage());
    }
  }

  private static void reaw(Exchange exchange) throws IOException {
    DSXTradeServiceRaw tradeService = (DSXTradeServiceRaw) exchange.getTradeService();
    Map<Long, DSXOrder> orders = null;
    try {
      orders = tradeService.getDSXActiveOrders("btcusd");
      for (Map.Entry<Long, DSXOrder> entry : orders.entrySet()) {
        System.out.println("id: " + entry.getKey() + ", orders:" + entry.getValue());
      }
      System.out.println(orders.toString());
    } catch (ExchangeException e) {
      System.out.println(e.getMessage());
    }
  }
}
