package com.xeiam.xchange.bitcoinde.service.polling;
import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitcoinde.BitcoindeAdapters;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author matthewdowney
 */
public class BitcoindeMarketDataService extends BitcoindeMarketDataServiceRaw implements PollingMarketDataService {
	Exchange exchange;
	/**
	 * Constructor
	 * @param exchange
	 */
	public BitcoindeMarketDataService(Exchange exchange) {
		super(exchange);
		this.exchange = exchange;
	}
	
	public void setApiKey(String key) {
		this.exchange.getExchangeSpecification().setApiKey(key);
	}
	
	@Override
	public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
		return BitcoindeAdapters.adaptTicker(getBitcoindeRate(exchange.getExchangeSpecification().getApiKey()), currencyPair);
	}
	@Override
	public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
		return BitcoindeAdapters.adaptOrderBook(getBitcoindeOrderBook(exchange.getExchangeSpecification().getApiKey()), currencyPair);
	}
	@Override
	public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
		return BitcoindeAdapters.adaptTrades(getBitcoindeTrades(exchange.getExchangeSpecification().getApiKey()), currencyPair);
	}
}
