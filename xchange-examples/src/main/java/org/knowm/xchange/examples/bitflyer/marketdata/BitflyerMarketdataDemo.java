package org.knowm.xchange.examples.bitflyer.marketdata;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitflyer.dto.account.BitflyerMarket;
import org.knowm.xchange.bitflyer.dto.marketdata.BitflyerTicker;
import org.knowm.xchange.bitflyer.service.BitflyerMarketDataServiceRaw;
import org.knowm.xchange.examples.bitflyer.BitflyerDemoUtils;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BitflyerMarketdataDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = BitflyerDemoUtils.createExchange();
    MarketDataService service = exchange.getMarketDataService();

    ticker(service);
  }

  private static void ticker(MarketDataService service) throws IOException {
    // Get the ticker/markets information
    BitflyerMarketDataServiceRaw serviceRaw = (BitflyerMarketDataServiceRaw) service;
    List<BitflyerMarket> markets = serviceRaw.getMarkets();
    System.out.println(markets);

    BitflyerTicker ticker = serviceRaw.getTicker();
    System.out.println(ticker);

    ticker = serviceRaw.getTicker("BTC_USD");
    System.out.println(ticker);
  }
}
