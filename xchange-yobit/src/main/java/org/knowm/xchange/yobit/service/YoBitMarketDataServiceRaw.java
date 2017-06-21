package org.knowm.xchange.yobit.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.yobit.YoBit;
import org.knowm.xchange.yobit.dto.marketdata.YoBitInfo;
import org.knowm.xchange.yobit.dto.marketdata.YoBitOrderBook;
import org.knowm.xchange.yobit.dto.marketdata.YoBitTicker;
import org.knowm.xchange.yobit.dto.marketdata.YoBitTickerReturn;
import org.knowm.xchange.yobit.dto.marketdata.YoBitTrades;

public class YoBitMarketDataServiceRaw extends YoBitBaseService<YoBit> {

  protected YoBitMarketDataServiceRaw(Exchange exchange) {
    super(YoBit.class, exchange);
  }

  public YoBitInfo getProducts() throws IOException {
    YoBitInfo data = coinbaseEx.getProducts();
    return data;
  }

  public YoBitTickerReturn getYoBitTicker(CurrencyPair currencyPair) throws IOException {
    return this.coinbaseEx.getTicker(currencyPair.base.getCurrencyCode().toLowerCase(),
        currencyPair.counter.getCurrencyCode().toLowerCase());
  }

  public YoBitOrderBook getOrderBookA(CurrencyPair currencyPair, Long limit) throws IOException {
    /*
     * if (!this.checkProductExists(currencyPair)) { return null; }
     */

    return this.coinbaseEx.getOrderBook(currencyPair.base.getCurrencyCode().toLowerCase(), currencyPair.counter.getCurrencyCode().toLowerCase(),
        limit);
  }

  public YoBitTrades getTrades(CurrencyPair currencyPair) throws IOException {
    return this.coinbaseEx.getTrades(currencyPair.base.getCurrencyCode().toLowerCase(), currencyPair.counter.getCurrencyCode().toLowerCase());
  }
}
