package com.xeiam.xchange.examples.btcchina.trade;

import java.io.IOException;
import java.util.Date;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btcchina.dto.trade.BTCChinaOrder;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaGetOrderResponse;
import com.xeiam.xchange.btcchina.service.polling.BTCChinaTradeServiceRaw;
import com.xeiam.xchange.examples.btcchina.BTCChinaExamplesUtils;

/**
 * Demo for {@link BTCChinaTradeServiceRaw#getBTCChinaOrder}.
 *
 * @author Joe Zhou
 */
public class BTCChinaGetOrderDemo {

  static Exchange btcchina = BTCChinaExamplesUtils.getExchange();
  static BTCChinaTradeServiceRaw tradeServiceRaw
    = (BTCChinaTradeServiceRaw) btcchina.getPollingTradeService();

  public static void main(String[] args) throws IOException {
    final long orderId = Long.parseLong(args[0]);
    final String market = args.length > 1 ? args[1] : null;

    BTCChinaGetOrderResponse response;
    if (market == null) {
      response = tradeServiceRaw.getBTCChinaOrder(orderId);
    } else {
      response = tradeServiceRaw.getBTCChinaOrder(orderId, market);
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
  }

}
