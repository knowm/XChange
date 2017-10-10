package org.knowm.xchange.binance.service.marketdata;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.binance.Binance;
import org.knowm.xchange.binance.dto.marketdata.BinanceOrderBook;
import org.knowm.xchange.binance.dto.marketdata.BinanceTicker;
import org.knowm.xchange.binance.service.BinanceBaseService;
import org.knowm.xchange.currency.CurrencyPair;

public class BinanceMarketDataServiceRaw extends BinanceBaseService<Binance> {

  public BinanceMarketDataServiceRaw(Exchange exchange) {
    super(Binance.class, exchange);
  }

  public BinanceTicker getBinanceTicker(CurrencyPair currencyPair) throws IOException {
    return service.getTicker("" + currencyPair.base + currencyPair.counter);
  }

  public BinanceOrderBook getBinanceOrderBook(CurrencyPair currencyPair) throws IOException {
    return service.getOrderBook("" + currencyPair.base + currencyPair.counter, "100");
  }
}
