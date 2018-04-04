package org.knowm.xchange.examples.cexio.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.cexio.CexIOExchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Author: brox Since: 2/6/14 */
public class TradesDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Cex.IO exchange API using default settings
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CexIOExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = exchange.getMarketDataService();

    // Get the latest trade data for GHs/BTC since tid=5635556
    Trades trades =
        marketDataService.getTrades(new CurrencyPair(Currency.GHs, Currency.BTC), 5909107);
    System.out.println("Trades Size= " + trades.getTrades().size());
    System.out.println(trades.toString());
  }
}
