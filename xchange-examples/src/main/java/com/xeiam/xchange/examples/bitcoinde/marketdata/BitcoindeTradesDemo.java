package com.xeiam.xchange.examples.bitcoinde.marketdata;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcoinde.BitcoindeExchange;
import com.xeiam.xchange.bitcoinde.dto.marketdata.BitcoindeTrade;
import com.xeiam.xchange.bitcoinde.service.polling.BitcoindeMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class BitcoindeTradesDemo {

  public static void main(String[] args) throws IOException {

    /* get the api key from args */
    if (args.length != 1) {
      System.err.println("Usage: java BitcoindeTradesDemo <api_key>");
      System.exit(1);
    }
    final String API_KEY = args[0];

    /* configure the exchange to use our api key */
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(BitcoindeExchange.class.getName());
    exchangeSpecification.setApiKey(API_KEY);

    /* create the exchange object */
    Exchange bitcoindeExchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    /* create a data service from the exchange */
    PollingMarketDataService marketDataService = bitcoindeExchange.getPollingMarketDataService();

    generic(marketDataService);
    raw((BitcoindeMarketDataServiceRaw) marketDataService);
  }

  public static void generic(PollingMarketDataService marketDataService) throws IOException {

    /* get Trades data */
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_EUR);
    List<Trade> allTrades = trades.getTrades();
    System.out.println("Number trades received: " + allTrades.size());
    for (Trade t : allTrades ){
    	System.out.println(t);
    }
    
  }

  public static void raw(BitcoindeMarketDataServiceRaw marketDataService) throws IOException {

    /* get BitcoindeTrades data */
    BitcoindeTrade[] bitcoindeTrades = marketDataService.getBitcoindeTrades();
    
    /* print each trade object */
    for (BitcoindeTrade bitcoindeTrade : bitcoindeTrades)
	    System.out.println(bitcoindeTrade);
  }
}
