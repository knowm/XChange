package org.knowm.xchange.yacuna.service.polling;

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
import org.knowm.xchange.yacuna.YacunaAdapters;
import org.knowm.xchange.yacuna.dto.marketdata.YacunaTicker;

/**
 * Created by Yingzhe on 12/27/2014.
 */
public class YacunaMarketDataService extends YacunaMarketDataServiceRaw implements PollingMarketDataService {

  public YacunaMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    // Request data
    YacunaTicker ticker = getYacunaTicker(currencyPair);

    // Adapt to XChange DTOs
    return YacunaAdapters.adaptTicker(ticker, currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    throw new NotYetImplementedForExchangeException();
  }
}
