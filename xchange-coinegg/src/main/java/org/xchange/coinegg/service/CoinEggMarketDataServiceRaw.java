package org.xchange.coinegg.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.xchange.coinegg.dto.marketdata.CoinEggOrders;
import org.xchange.coinegg.dto.marketdata.CoinEggTicker;
import org.xchange.coinegg.dto.marketdata.CoinEggTrade;

public class CoinEggMarketDataServiceRaw extends CoinEggBaseService {

  public CoinEggMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  // TODO: Exception Handling - See Bitfinex
  public CoinEggTicker getCoinEggTicker(String coin) throws IOException {
    return coinEgg.getTicker(coin);
  }

  // TODO: Exception Handling - See Bitfinex
  public CoinEggTrade[] getCoinEggTrades(String coin) throws IOException {
    return coinEgg.getTrades(coin);
  }

  // TODO: Exception Handling - See Bitfinex
  public CoinEggOrders getCoinEggOrders(String coin) throws IOException {
    return coinEgg.getOrders(coin);
  }
}
