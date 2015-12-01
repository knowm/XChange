package com.xeiam.xchange.examples.cexio.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.cexio.CexIOExchange;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Author: brox Since: 2/6/14
 */

public class TradesDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Cex.IO exchange API using default settings
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CexIOExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = exchange.getPollingMarketDataService();

    // Get the latest trade data for GHs/BTC since tid=5635556
    Trades trades = marketDataService.getTrades(new CurrencyPair(Currency.GHs, Currency.BTC), 5909107);
    System.out.println("Trades Size= " + trades.getTrades().size());
    System.out.println(trades.toString());
  }

}
