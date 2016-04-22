package org.knowm.xchange.bitso.service.polling;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitso.BitsoAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author Piotr Ładyżyński
 */
public class BitsoMarketDataService extends BitsoMarketDataServiceRaw implements PollingMarketDataService {

  public BitsoMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return BitsoAdapters.adaptTicker(getBitsoTicker(), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return BitsoAdapters.adaptOrderBook(getBitsoOrderBook(), currencyPair, 1000);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return BitsoAdapters.adaptTrades(getBitsoTransactions(args), currencyPair);
  }
}
