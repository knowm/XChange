package org.knowm.xchange.bitcoinde.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcoinde.BitcoindeAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * @author matthewdowney
 */
public class BitcoindeMarketDataService extends BitcoindeMarketDataServiceRaw implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitcoindeMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    return BitcoindeAdapters.adaptOrderBook(getBitcoindeOrderBook(), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    Integer since = null; // all trades possible
    if (args != null && args.length > 0) {
      // parameter 1, if present, is the since param
      if (args[0] instanceof Integer) {
        since = (Integer) args[0];
      } else {
        throw new IllegalArgumentException("Extra argument #1,  'since', must be an int (was " + args[0].getClass() + ")");
      }
    }

    return BitcoindeAdapters.adaptTrades(getBitcoindeTrades(since), currencyPair);
  }
}
