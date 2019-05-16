package org.knowm.xchange.examples.livecoin.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.livecoin.LivecoinExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Demonstrate requesting Trade from Livecoin. */
public class TradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange livecoinExchange =
        ExchangeFactory.INSTANCE.createExchange(LivecoinExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = livecoinExchange.getMarketDataService();

    System.out.println("fetching data...");

    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_USD);

    System.out.println("received data.");

    for (Trade trade : trades.getTrades()) {
      System.out.println(
          trade.getType()
              + " "
              + trade.getCurrencyPair()
              + " Price: "
              + trade.getPrice()
              + " Amount: "
              + trade.getOriginalAmount());
    }
  }
}
