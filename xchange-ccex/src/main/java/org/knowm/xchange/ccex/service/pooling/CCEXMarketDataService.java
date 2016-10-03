package org.knowm.xchange.ccex.service.pooling;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ccex.CCEXAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author Andraž Prinčič
 */
public class CCEXMarketDataService extends CCEXMarketDataServiceRaw implements PollingMarketDataService {

	public CCEXMarketDataService(Exchange exchange) {
		super(exchange);
	}

	@Override
	public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
		return CCEXAdapters.adaptOrderBook(getCCEXOrderBook(currencyPair), currencyPair);
	}

	@Override
	public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws ExchangeException,
			NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
		return CCEXAdapters.adaptTrades(getCCEXTrades(currencyPair), currencyPair);
	}

}