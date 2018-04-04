package org.knowm.xchange.examples.gdax;

import java.io.IOException;
import java.util.Arrays;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.gdax.GDAXExchange;
import org.knowm.xchange.gdax.dto.marketdata.GDAXTrade;
import org.knowm.xchange.gdax.service.GDAXMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class GDAXTradesDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(GDAXExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();

    generic(marketDataService);
    raw((GDAXMarketDataServiceRaw) marketDataService);
  }

  public static void generic(MarketDataService marketDataService) throws IOException {

    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_USD);
    System.out.println(trades);
  }

  public static void raw(GDAXMarketDataServiceRaw marketDataServiceRaw) throws IOException {

    GDAXTrade[] trades = marketDataServiceRaw.getGDAXTrades(CurrencyPair.BTC_USD);
    System.out.println(Arrays.toString(trades));
  }
}
