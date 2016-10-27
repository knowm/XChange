package org.knowm.xchange.yobit.service;

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
import org.knowm.xchange.yobit.YoBitAdapters;

public class YoBitMarketDataService extends YoBitMarketDataServiceRaw implements PollingMarketDataService {

	public YoBitMarketDataService(Exchange exchange) {
		super(exchange);
	}

	@Override
	public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws ExchangeException,
			NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
		return YoBitAdapters.adaptOrderBook(getOrderBook(currencyPair), currencyPair);
	}

	@Override
	public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
		return YoBitAdapters.adaptTrades(getTrades(currencyPair), currencyPair);
	}
}
