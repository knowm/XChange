package com.xeiam.xchange.bitcoinde.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitcoinde.BitcoindeAdapters;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author matthewdowney
 */
public class BitcoindeMarketDataService extends BitcoindeMarketDataServiceRaw implements PollingMarketDataService {

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

    return BitcoindeAdapters.adaptTrades(getBitcoindeTrades(), currencyPair);
  }
}
