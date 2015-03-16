package com.xeiam.xchange.examples.btcchina.trade;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btcchina.BTCChinaExchange;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaGetOrdersRequest;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaGetOrdersResponse;
import com.xeiam.xchange.btcchina.service.polling.BTCChinaTradeService;
import com.xeiam.xchange.btcchina.service.polling.BTCChinaTradeServiceRaw;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.examples.btcchina.BTCChinaExamplesUtils;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

/**
 * Demo for {@link BTCChinaTradeServiceRaw#getBTCChinaOrders(Boolean, String, Integer, Integer)} . and {@link BTCChinaTradeService#getOpenOrders()}.
 */
public class BTCChinaGetOrdersDemo {

  static Exchange exchange = BTCChinaExamplesUtils.getExchange();
  static PollingTradeService tradeService = exchange.getPollingTradeService();
  static BTCChinaTradeServiceRaw tradeServiceRaw = (BTCChinaTradeServiceRaw) exchange.getPollingTradeService();

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
