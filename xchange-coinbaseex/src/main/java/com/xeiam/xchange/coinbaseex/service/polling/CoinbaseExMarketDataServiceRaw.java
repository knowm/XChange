package com.xeiam.xchange.coinbaseex.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinbaseex.CoinbaseEx;
import com.xeiam.xchange.coinbaseex.dto.marketdata.CoinbaseExProduct;
import com.xeiam.xchange.coinbaseex.dto.marketdata.CoinbaseExProductBook;
import com.xeiam.xchange.coinbaseex.dto.marketdata.CoinbaseExProductStats;
import com.xeiam.xchange.coinbaseex.dto.marketdata.CoinbaseExProductTicker;
import com.xeiam.xchange.currency.CurrencyPair;

import java.io.IOException;

/**
 * Created by Yingzhe on 4/6/2015.
 */
public class CoinbaseExMarketDataServiceRaw extends CoinbaseExBasePollingService<CoinbaseEx> {

  public CoinbaseExMarketDataServiceRaw(Exchange exchange) {

    super(CoinbaseEx.class, exchange);
  }

  public CoinbaseExProductTicker getCoinbaseExProductTicker(CurrencyPair currencyPair) throws IOException {

    if (!this.checkProductExists(currencyPair)) {
      return null;
    }

    CoinbaseExProductTicker tickerReturn = this.coinbaseEx.getProductTicker(currencyPair.baseSymbol, currencyPair.counterSymbol);
    return tickerReturn;
  }

  public CoinbaseExProductStats getCoinbaseExProductStats(CurrencyPair currencyPair) throws IOException {

    if (!this.checkProductExists(currencyPair)) {
      return null;
    }

    CoinbaseExProductStats statsReturn = this.coinbaseEx.getProductStats(currencyPair.baseSymbol, currencyPair.counterSymbol);
    return statsReturn;
  }

  public CoinbaseExProductBook getCoinbaseExProductOrderBook(CurrencyPair currencyPair) throws IOException {

    if (!this.checkProductExists(currencyPair)) {
      return null;
    }

    CoinbaseExProductBook book = this.coinbaseEx.getProductOrderBook(currencyPair.baseSymbol, currencyPair.counterSymbol);
    return book;
  }

  private boolean checkProductExists(CurrencyPair currencyPair) throws IOException {

    boolean currencyPairSupported = false;
    for (CurrencyPair cp : this.getExchangeSymbols()) {
      if (cp.baseSymbol.equalsIgnoreCase(currencyPair.baseSymbol) && cp.counterSymbol.equalsIgnoreCase(currencyPair.counterSymbol)) {
        currencyPairSupported = true;
        break;
      }
    }

    return currencyPairSupported;
  }
}
