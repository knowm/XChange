package com.xeiam.xchange.examples.kraken.trade;

import java.io.IOException;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.trade.TradeMetaData;
import com.xeiam.xchange.examples.kraken.KrakenExampleUtils;
import com.xeiam.xchange.kraken.service.polling.KrakenTradeServiceRaw;

public class KrakenTradeMetaDataDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Kraken exchange API using default settings
    Exchange krakenExchange = KrakenExampleUtils.createTestExchange();

    metadata(krakenExchange);
  }

  private static void metadata(Exchange krakenExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    KrakenTradeServiceRaw krakenTradeServiceRaw = (KrakenTradeServiceRaw) krakenExchange.getPollingTradeService();

    Map<CurrencyPair, TradeMetaData> mm = krakenTradeServiceRaw.getTradeMetaDataMap();
    System.out.println(mm);
  }

}
