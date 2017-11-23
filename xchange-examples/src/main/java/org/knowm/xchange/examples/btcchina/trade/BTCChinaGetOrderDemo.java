package org.knowm.xchange.examples.btcchina.trade;

import java.io.IOException;
import java.util.Date;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcchina.dto.trade.BTCChinaOrder;
import org.knowm.xchange.btcchina.dto.trade.BTCChinaOrderDetail;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaGetOrderResponse;
import org.knowm.xchange.btcchina.service.rest.BTCChinaTradeServiceRaw;
import org.knowm.xchange.examples.btcchina.BTCChinaExamplesUtils;

/**
 * Demo for {@link BTCChinaTradeServiceRaw#getBTCChinaOrder}.
 */
public class BTCChinaGetOrderDemo {

  static Exchange btcchina = BTCChinaExamplesUtils.getExchange();
  static BTCChinaTradeServiceRaw tradeServiceRaw = (BTCChinaTradeServiceRaw) btcchina.getTradeService();

  public static void main(String[] args) throws IOException {

    final int orderId = Integer.parseInt(args[0]);
    final String market = args.length > 1 ? args[1] : null;
    final Boolean withdetail = args.length > 2 ? Boolean.valueOf(args[2]) : null;

    BTCChinaGetOrderResponse response;
    if (market == null) {
      response = tradeServiceRaw.getBTCChinaOrder(orderId);
    } else if (withdetail == null) {
      response = tradeServiceRaw.getBTCChinaOrder(orderId, market);
    } else {
      response = tradeServiceRaw.getBTCChinaOrder(orderId, market, withdetail);
    }
    System.out.println(response);

    BTCChinaOrder order = response.getResult().getOrder();
    System.out.println("OrderID:\t" + order.getId());
    System.out.println("Side:\t" + order.getType());
    System.out.println("Price:\t" + order.getPrice());
    System.out.println("Currency: \t" + order.getCurrency());
    System.out.println("LeavesQty:\t" + order.getAmount());
    System.out.println("OrderQty:\t" + order.getAmountOriginal());
    System.out.println("CumQty:\t" + order.getAmountOriginal().subtract(order.getAmount()));
    System.out.println("Date:\t" + new Date(order.getDate() * 1000));
    System.out.println("Status:\t" + order.getStatus());
    if (order.getDetails() != null) {
      for (BTCChinaOrderDetail detail : order.getDetails()) {
        System.out.println("--");
        System.out.println("dateline:\t" + detail.getDateline());
        System.out.println("price:\t" + detail.getPrice());
        System.out.println("amount:\t" + detail.getAmount());
      }
    }
  }

}
