package com.xeiam.xchange.examples.anx.v2.service.marketdata.polling;

import java.io.IOException;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.anx.v2.dto.marketdata.ANXTradeMetaData;
import com.xeiam.xchange.anx.v2.service.polling.ANXTradeServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.examples.anx.v2.ANXExamplesUtils;

public class ANXTradeMetaDataDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get ANX exchange API using default settings
    Exchange anxExchange = ANXExamplesUtils.createExchange();

    metadata(anxExchange);
  }

  private static void metadata(Exchange exchange) throws IOException {

    ANXTradeServiceRaw anxTradeServiceRaw = (ANXTradeServiceRaw) exchange.getPollingTradeService();

    Map<CurrencyPair, ANXTradeMetaData> mm = anxTradeServiceRaw.getTradeMetaDataMap();
    System.out.println(mm);
  }

}
