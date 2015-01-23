package com.xeiam.xchange.examples.anx.v2.service.marketdata.polling;

import java.io.IOException;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.trade.TradeMetaData;
import com.xeiam.xchange.examples.anx.v2.ANXExamplesUtils;

public class TradeMetaDataDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get ANX exchange API using default settings
    Exchange krakenExchange = ANXExamplesUtils.createExchange();

    metadata(krakenExchange);
  }

  private static void metadata(Exchange exchange) throws IOException {

    Map<CurrencyPair, ? extends TradeMetaData> mm = exchange.getPollingTradeService().getTradeMetaDataMap();
    System.out.println(mm);
  }

}
