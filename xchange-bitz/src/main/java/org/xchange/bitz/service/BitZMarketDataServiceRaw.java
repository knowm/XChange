package org.xchange.bitz.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.xchange.bitz.dto.marketdata.BitZOrders;
import org.xchange.bitz.dto.marketdata.BitZTrades;
import org.xchange.bitz.dto.marketdata.BitZTicker;

public class BitZMarketDataServiceRaw extends BitZBaseService {

	public BitZMarketDataServiceRaw(Exchange exchange) {
		super(exchange);
	}
	
	// TODO: Exception Handling - See Bitfinex
	public BitZTicker getBitZTicker(String pair) throws IOException {
	    return bitz.getTickerResult(pair).getData();
	}
	
	// TODO: Exception Handling - See Bitfinex
	public BitZOrders getBitZOrders(String pair) throws IOException {
		return bitz.getOrdersResult(pair).getData();
	}
	
	// TODO: Exception Handling - See Bitfinex
	public BitZTrades getBitZTrades(String pair) throws IOException {
		return bitz.getTradesResult(pair).getData();
	}
}
