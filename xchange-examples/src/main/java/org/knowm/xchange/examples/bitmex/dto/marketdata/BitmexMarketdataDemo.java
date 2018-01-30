package org.knowm.xchange.examples.bitmex.dto.marketdata;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmex.dto.account.BitmexTicker;
import org.knowm.xchange.bitmex.service.BitmexMarketDataServiceRaw;
import org.knowm.xchange.examples.bitmex.BitmexDemoUtils;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BitmexMarketdataDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = BitmexDemoUtils.createExchange();
    MarketDataService service = exchange.getMarketDataService();

    ticker(service);
  }

  private static void ticker(MarketDataService service) throws IOException {

    // Get the ticker/markets information
    BitmexMarketDataServiceRaw serviceRaw = (BitmexMarketDataServiceRaw) service;
    List<BitmexTicker> tickers = serviceRaw.getActiveTickers();
    System.out.println(tickers);

    tickers = serviceRaw.getTicker("Xbt");
    System.out.println(tickers);

    List<BitmexTicker> ticker = serviceRaw.getTicker("XBt");
    System.out.println(ticker);
  }
}