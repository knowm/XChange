package org.knowm.xchange.examples.bitcoinde.marketdata;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeTrade;
import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeTradesWrapper;
import org.knowm.xchange.bitcoinde.service.BitcoindeMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.examples.bitcoinde.ExchangeUtils;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BitcoindeTradesDemo {

  public static void main(String[] args) throws IOException {

    Exchange bitcoindeExchange = ExchangeUtils.createExchangeFromJsonConfiguration();

    /* create a data service from the exchange */
    MarketDataService marketDataService = bitcoindeExchange.getMarketDataService();

    generic(marketDataService);
    raw((BitcoindeMarketDataServiceRaw) marketDataService);
  }

  public static void generic(MarketDataService marketDataService) throws IOException {

    /* get Trades data */
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_EUR);
    List<Trade> allTrades = trades.getTrades();
    System.out.println("Number trades received: " + allTrades.size());
    for (Trade t : allTrades) {
      System.out.println(t);
    }

  }

  public static void raw(BitcoindeMarketDataServiceRaw marketDataService) throws IOException {

    /* get BitcoindeTrades data */
    BitcoindeTradesWrapper bitcoindeTrades = marketDataService.getBitcoindeTrades(2835279);

    /* print each trade object */
    for (BitcoindeTrade bitcoindeTrade : bitcoindeTrades.getTrades())
      System.out.println(bitcoindeTrade);
  }
}
