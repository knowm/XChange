package org.knowm.xchange.examples.btcchina.trade;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcchina.BTCChinaExchange;
import org.knowm.xchange.btcchina.dto.trade.request.BTCChinaGetOrdersRequest;
import org.knowm.xchange.btcchina.dto.trade.response.BTCChinaGetOrdersResponse;
import org.knowm.xchange.btcchina.service.rest.BTCChinaTradeService;
import org.knowm.xchange.btcchina.service.rest.BTCChinaTradeServiceRaw;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.btcchina.BTCChinaExamplesUtils;
import org.knowm.xchange.service.trade.TradeService;

/**
 * Demo for {@link BTCChinaTradeServiceRaw#getBTCChinaOrders(Boolean, String, Integer, Integer)} . and {@link BTCChinaTradeService#getOpenOrders()}.
 */
public class BTCChinaGetOrdersDemo {

  static Exchange exchange = BTCChinaExamplesUtils.getExchange();
  static TradeService tradeService = exchange.getTradeService();
  static BTCChinaTradeServiceRaw tradeServiceRaw = (BTCChinaTradeServiceRaw) exchange.getTradeService();

  public static void main(String[] args) throws IOException {

    BTCChinaGetOrdersResponse defaultParams = tradeServiceRaw.getBTCChinaOrders(null, null, null, null, null, null);
    System.out.println(defaultParams);

    BTCChinaGetOrdersResponse btcCny = tradeServiceRaw.getBTCChinaOrders(true, "BTCCNY", null, null);
    System.out.println(btcCny);

    BTCChinaGetOrdersResponse ltcCny = tradeServiceRaw.getBTCChinaOrders(true, "LTCCNY", null, null);
    System.out.println(ltcCny);

    BTCChinaGetOrdersResponse ltcBtc = tradeServiceRaw.getBTCChinaOrders(true, "LTCBTC", null, null);
    System.out.println(ltcBtc);

    BTCChinaGetOrdersResponse all = tradeServiceRaw.getBTCChinaOrders(true, BTCChinaGetOrdersRequest.ALL_MARKET, null, null);
    System.out.println(all);

    BTCChinaGetOrdersResponse withdetail = tradeServiceRaw.getBTCChinaOrders(Boolean.FALSE, BTCChinaExchange.DEFAULT_MARKET, null, null, null,
        Boolean.TRUE);
    System.out.println(withdetail);

    // Generic
    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders);
  }

}
