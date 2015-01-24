package com.xeiam.xchange.bitkonan.service.polling;

import java.io.IOException;
import java.util.Collection;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitkonan.BitKonanAdapters;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author Piotr Ładyżyński
 */
public class BitKonanMarketDataService extends BitKonanMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor Initialize common properties from the exchange specification
   *
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public BitKonanMarketDataService(ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> nonceFactory) {

    super(exchangeSpecification, nonceFactory);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    if (currencyPair.equals(CurrencyPair.BTC_USD)) {
      return BitKonanAdapters.adaptTicker(getBitKonanTickerBTC(), currencyPair);
    }
    else {
      throw new NotYetImplementedForExchangeException();
    }
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    if (currencyPair.equals(CurrencyPair.BTC_USD)) {
      return BitKonanAdapters.adaptOrderBook(getBitKonanOrderBookBTC());
    }
    else {
      throw new NotYetImplementedForExchangeException();
    }
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public Collection<CurrencyPair> getExchangeSymbols() throws IOException {

    throw new NotAvailableFromExchangeException();
  }
}
