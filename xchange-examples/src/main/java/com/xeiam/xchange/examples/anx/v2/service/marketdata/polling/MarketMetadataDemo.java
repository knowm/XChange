package com.xeiam.xchange.examples.anx.v2.service.marketdata.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.examples.anx.v2.ANXExamplesUtils;
import com.xeiam.xchange.service.polling.trade.TradeMetaData;

import java.io.IOException;
import java.util.Map;

public class MarketMetadataDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Kraken exchange API using default settings
    Exchange krakenExchange = ANXExamplesUtils.createExchange();

    metadata(krakenExchange);
  }

  private static void metadata(Exchange exchange) throws IOException {
    Map<CurrencyPair, ? extends TradeMetaData> mm = exchange.getPollingTradeService().getTradeServiceHelperMap();
    System.out.println(mm);
  }

}
