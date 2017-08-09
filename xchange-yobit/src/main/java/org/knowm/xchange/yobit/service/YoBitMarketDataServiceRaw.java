package org.knowm.xchange.yobit.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.yobit.YoBit;
import org.knowm.xchange.yobit.dto.marketdata.YoBitInfo;
import org.knowm.xchange.yobit.dto.marketdata.YoBitOrderBook;
import org.knowm.xchange.yobit.dto.marketdata.YoBitTickerReturn;
import org.knowm.xchange.yobit.dto.marketdata.YoBitTrades;

import java.io.IOException;

public class YoBitMarketDataServiceRaw extends YoBitBaseService<YoBit> {

  protected YoBitMarketDataServiceRaw(Exchange exchange) {
    super(YoBit.class, exchange);
  }

  public YoBitInfo getProducts() throws IOException {
    return service.getProducts();
  }

  public YoBitTickerReturn getYoBitTicker(CurrencyPair currencyPair) throws IOException {
    return service.getTicker(
        currencyPair.base.getCurrencyCode().toLowerCase(),
        currencyPair.counter.getCurrencyCode().toLowerCase()
    );
  }

  public YoBitOrderBook getOrderBookA(CurrencyPair currencyPair, Long limit) throws IOException {
    return service.getOrderBook(
        currencyPair.base.getCurrencyCode().toLowerCase(),
        currencyPair.counter.getCurrencyCode().toLowerCase(),
        limit
    );
  }

  public YoBitTrades getTrades(CurrencyPair currencyPair) throws IOException {
    return service.getTrades(
        currencyPair.base.getCurrencyCode().toLowerCase(),
        currencyPair.counter.getCurrencyCode().toLowerCase()
    );
  }
}
