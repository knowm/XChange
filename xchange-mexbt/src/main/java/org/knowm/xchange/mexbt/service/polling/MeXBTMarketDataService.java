package org.knowm.xchange.mexbt.service.polling;

import static org.knowm.xchange.mexbt.MeXBTAdapters.adaptOrderBook;
import static org.knowm.xchange.mexbt.MeXBTAdapters.adaptTicker;
import static org.knowm.xchange.mexbt.MeXBTAdapters.adaptTrades;
import static org.knowm.xchange.mexbt.MeXBTAdapters.toCurrencyPair;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;

public class MeXBTMarketDataService extends MeXBTMarketDataServiceRaw implements PollingMarketDataService {

  public MeXBTMarketDataService(Exchange exchange) {
    super(exchange);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return adaptTicker(currencyPair, getTicker(toCurrencyPair(currencyPair)));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws ExchangeException, IOException {
    return adaptOrderBook(currencyPair, getOrderBook(toCurrencyPair(currencyPair)));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    final Long since;
    if (args.length > 0 && args[0] instanceof Number) {
      since = ((Number) args[0]).longValue();
    } else {
      since = null;
    }
    return adaptTrades(currencyPair, getTrades(toCurrencyPair(currencyPair), since));
  }

}
