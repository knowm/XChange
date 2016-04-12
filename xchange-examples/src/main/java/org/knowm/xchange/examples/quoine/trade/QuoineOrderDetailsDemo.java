package org.knowm.xchange.examples.quoine.trade;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.examples.quoine.QuoineExamplesUtils;
import org.knowm.xchange.quoine.dto.trade.QuoineOrderDetailsResponse;
import org.knowm.xchange.quoine.service.polling.QuoineTradeServiceRaw;
import org.knowm.xchange.service.polling.trade.PollingTradeService;

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
