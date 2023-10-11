package org.knowm.xchange.service.marketdata;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.*;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.service.marketdata.params.Params;
import org.knowm.xchange.service.trade.params.CandleStickDataParams;

/**
 * Interface to provide the following to {@link Exchange}:
 *
 * <ul>
 *   <li>Standard methods available to explore the market data
 * </ul>
 *
 * <p>The implementation of this service is expected to be based on a client polling mechanism of
 * some kind
 */
public interface MarketDataService extends BaseService {

  /**
   * Get a ticker representing the current exchange rate
   *
   * @return The Ticker, null if some sort of error occurred. Implementers should log the error.
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   *     request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   *     requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   *     requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  @Deprecated
  default Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    throw new NotYetImplementedForExchangeException("getTicker");
  }

  /**
   * Get a ticker representing the current exchange rate
   *
   * @return The Ticker, null if some sort of error occurred. Implementers should log the error.
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   *     request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   *     requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   *     requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  default Ticker getTicker(Instrument instrument, Object... args) throws IOException {
    throw new NotYetImplementedForExchangeException("getTicker");
  }

  /**
   * Get the tickers representing the current exchange rate for the provided parameters
   *
   * @return The Tickers, null if some sort of error occurred. Implementers should log the error.
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   *     request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   *     requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   *     requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  default List<Ticker> getTickers(Params params) throws IOException {
    throw new NotYetImplementedForExchangeException("getTickers");
  }

  /**
   * Get an order book representing the current offered exchange rates (market depth)
   *
   * @param args Optional arguments. Exchange-specific
   * @return The OrderBook, null if some sort of error occurred. Implementers should log the error.
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   *     request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   *     requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   *     requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  @Deprecated
  default OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    throw new NotYetImplementedForExchangeException("getOrderBook");
  }

  /**
   * Get an order book representing the current offered exchange rates (market depth)
   *
   * @param args Optional arguments. Exchange-specific
   * @return The OrderBook, null if some sort of error occurred. Implementers should log the error.
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   *     request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   *     requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   *     requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  default OrderBook getOrderBook(Instrument instrument, Object... args) throws IOException {
    throw new NotYetImplementedForExchangeException("getOrderBook");
  }

  /**
   * Get an order book representing the current offered exchange rates (market depth)
   *
   * @param params Exchange-specific
   * @return The OrderBook, null if some sort of error occurred. Implementers should log the error.
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   *     request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   *     requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   *     requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  default OrderBook getOrderBook(Params params) throws IOException {
    throw new NotYetImplementedForExchangeException("getOrderBook");
  }

  /**
   * Get the trades recently performed by the exchange
   *
   * @param args Optional arguments. Exchange-specific
   * @return The Trades, null if some sort of error occurred. Implementers should log the error.
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   *     request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   *     requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   *     requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  @Deprecated
  default Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    throw new NotYetImplementedForExchangeException("getTrades");
  }

  /**
   * Get the trades recently performed by the exchange
   *
   * @param args Optional arguments. Exchange-specific
   * @return The Trades, null if some sort of error occurred. Implementers should log the error.
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   *     request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   *     requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   *     requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  default Trades getTrades(Instrument instrument, Object... args) throws IOException {
    throw new NotYetImplementedForExchangeException("getTrades");
  }

  /**
   * Get the trades recently performed by the exchange
   *
   * @param params Exchange-specific
   * @return The Trades, null if some sort of error occurred. Implementers should log the error.
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   *     request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   *     requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   *     requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  default Trades getTrades(Params params) throws IOException {
    throw new NotYetImplementedForExchangeException("getTrades");
  }


  /**
   * Get the CandleStickData for given currency between startDate to endDate.
   *
   * @param currencyPair currencyPair.
   * @param params Params for query, including start(e.g. march 2022.) and end date, period etc.,
   * @return The CandleStickData, null if some sort of error occurred. Implementers should log the error.
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   * request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   * requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   * requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  default CandleStickData getCandleStickData(CurrencyPair currencyPair, CandleStickDataParams params) throws IOException {
    throw new NotYetImplementedForExchangeException("getCandleStickData");
  }

  /**
   * Get the FundingRates for all perpetual contracts of the platform.
   *
   * @return The FundingRates, null if some sort of error occurred. Implementers should log the error.
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   * request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   * requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   * requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  default FundingRates getFundingRates() throws IOException {
    throw new NotYetImplementedForExchangeException("getFundingRates");
  }

  /**
   * Get the FundingRate for specific instrument.
   *
   * @param instrument Instrument to get the funding rate.
   * @return The FundingRate, null if some sort of error occurred. Implementers should log the error.
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   * request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   * requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   * requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  default FundingRate getFundingRate(Instrument instrument) throws IOException {
    throw new NotYetImplementedForExchangeException("getFundingRate");
  }

  /**
   * Get currencies currently provided by the exchange
   *
   * @return The list of currencies, null if some sort of error occurred. Implementers should log
   *     the error.
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   *     request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   *     requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   *     requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  default Map<Currency, CurrencyMetaData> getCurrencies() throws IOException {
    throw new NotYetImplementedForExchangeException("getCurrencies");
  }

  /**
   * Get instruments/currency pairs currently provided by the exchange
   *
   * @return The list of instruments/currency pairs, null if some sort of error occurred.
   *     Implementers should log the error.
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   *     request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   *     requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   *     requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  default Map<Instrument, InstrumentMetaData> getInstruments() throws IOException {
    throw new NotYetImplementedForExchangeException("getInstruments");
  }
}
