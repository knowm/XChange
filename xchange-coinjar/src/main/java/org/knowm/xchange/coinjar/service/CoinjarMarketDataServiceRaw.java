package org.knowm.xchange.coinjar.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinjar.CoinjarException;
import org.knowm.xchange.coinjar.dto.data.CoinjarOrderBook;
import org.knowm.xchange.coinjar.dto.data.CoinjarTicker;

class CoinjarMarketDataServiceRaw extends CoinjarBaseService {

  CoinjarMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  CoinjarTicker getTicker(String product) throws CoinjarException, IOException {
    return coinjarData.getTicker(product);
  }

  CoinjarOrderBook getOrderBook(String product) throws CoinjarException, IOException {
    return coinjarData.getOrderBook(product, 2);
  }
}
