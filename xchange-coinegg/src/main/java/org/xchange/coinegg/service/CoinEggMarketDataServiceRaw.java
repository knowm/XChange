package org.xchange.coinegg.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.xchange.coinegg.dto.marketdata.CoinEggOrder;
import org.xchange.coinegg.dto.marketdata.CoinEggTicker;
import org.xchange.coinegg.dto.marketdata.CoinEggTrades;

public class CoinEggMarketDataServiceRaw extends CoinEggBaseService {

	public CoinEggMarketDataServiceRaw(Exchange exchange) {
		super(exchange);
	}
	
	 // TODO: Exception Handling - See Bitfinex
  public CoinEggTicker getCoinEggTicker(String coin) throws IOException {
      return coinEgg.getTicker(coin);
  }
  
  // TODO: Exception Handling - See Bitfinex
  public CoinEggTrades getCoinEggTrades(String coin) throws IOException {
    return coinEgg.getTrades(coin);
  }
  
  // TODO: Exception Handling - See Bitfinex
  public CoinEggOrder[] getCoinEggOrders(String coin) throws IOException {
    return coinEgg.getOrders(coin);
  }
}
