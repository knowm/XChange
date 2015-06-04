package com.xeiam.xchange.examples.quoine.trade;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.examples.quoine.QuoineExamplesUtils;
import com.xeiam.xchange.quoine.dto.trade.QuoineOrderDetailsResponse;
import com.xeiam.xchange.quoine.service.polling.QuoineTradeServiceRaw;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

public class QuoineOrderDetailsDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = QuoineExamplesUtils.createExchange();
    PollingTradeService tradeService = exchange.getPollingTradeService();

    raw((QuoineTradeServiceRaw) tradeService);
  }

  private static void raw(QuoineTradeServiceRaw tradeServiceRaw) throws IOException {

    // get order details
    QuoineOrderDetailsResponse QuoineOrderDetailsResponse = tradeServiceRaw.getQuoineOrderDetails("52364");
    System.out.println(QuoineOrderDetailsResponse.toString());

  }

}
