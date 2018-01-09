package org.xchange.bitz.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.xchange.bitz.BitZAdapters;
import org.xchange.bitz.BitZUtils;

public class BitZMarketDataService extends BitZMarketDataServiceRaw implements MarketDataService {

	public BitZMarketDataService(Exchange exchange) {
		super(exchange);
	}

	public List<Ticker> getTickers(Object... args) throws IOException {
    return BitZAdapters.adapterTickers(getBitZTickerAll());
  }

	
	@Override
	public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
		return BitZAdapters.adaptTicker(getBitZTicker(BitZUtils.toPairString(currencyPair)), currencyPair);
	}

	@Override
	public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
		return BitZAdapters.adaptOrders(getBitZOrders(BitZUtils.toPairString(currencyPair)), currencyPair);
	}

	@Override
	public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
		return BitZAdapters.adaptTrades(getBitZTrades(BitZUtils.toPairString(currencyPair)), currencyPair);
	}
	
	
	
	// TODO: Implement Exchange Specific Services
	// Including: TickerAll, Kline (Historic Data)
}
