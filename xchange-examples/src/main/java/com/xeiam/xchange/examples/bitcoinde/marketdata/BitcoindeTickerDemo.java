package com.xeiam.xchange.examples.bitcoinde.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcoinde.BitcoindeExchange;
import com.xeiam.xchange.bitcoinde.dto.marketdata.BitcoindeRate;
import com.xeiam.xchange.bitcoinde.service.polling.BitcoindeMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class BitcoindeTickerDemo {

  public void main(String[] args) throws IOException {

    /* get the api key from args */
    if (args.length != 1) {
      System.err.println("Usage: java BitcoindeTickerDemo <api_key>");
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

  public void generic(PollingMarketDataService marketDataService) throws IOException {

    /* get Ticker data */
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_EUR);
    System.out.println(ticker.toString());
  }

  public void raw(BitcoindeMarketDataServiceRaw marketDataService) throws IOException {

    /* get BitcoindeRate data (Bitcoinde does not provide a ticker, only and exchange rate) */
    BitcoindeRate bitcoindeRate = marketDataService.getBitcoindeRate();
    System.out.println(bitcoindeRate.toString());
  }

}