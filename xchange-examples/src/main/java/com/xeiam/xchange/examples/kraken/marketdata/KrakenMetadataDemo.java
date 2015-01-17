package com.xeiam.xchange.examples.kraken.marketdata;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.TradeServiceHelper;
import com.xeiam.xchange.examples.kraken.KrakenExampleUtils;

import java.io.IOException;
import java.util.Map;

public class KrakenMetadataDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Kraken exchange API using default settings
    Exchange krakenExchange = KrakenExampleUtils.createTestExchange();

    metadata(krakenExchange);
  }

  private static void metadata(Exchange krakenExchange) throws IOException {
    Map<CurrencyPair, ? extends TradeServiceHelper> mm = krakenExchange.getPollingTradeService().getTradeServiceHelperMap();
    System.out.println(mm);
  }

}
