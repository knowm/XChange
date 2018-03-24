package org.knowm.xchange.cryptocompare.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptocompare.dto.marketdata.CryptocompareOHLCV;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * <p>
 * Implementation of the generic market data service for Cryptocompare
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class CryptocompareMarketDataService extends CryptocompareMarketDataServiceRaw implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptocompareMarketDataService(Exchange exchange) {

    super(exchange);
  }

	@Override
	public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
		throw new NotYetImplementedForExchangeException();
	}
	
	@Override
	public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
		throw new NotYetImplementedForExchangeException();
	}
	
	@Override
	public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
		throw new NotYetImplementedForExchangeException();
	}
}
