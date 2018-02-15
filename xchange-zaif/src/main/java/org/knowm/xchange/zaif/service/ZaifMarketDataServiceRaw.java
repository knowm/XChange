package org.knowm.xchange.zaif.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.zaif.dto.ZaifException;
import org.knowm.xchange.zaif.dto.marketdata.ZaifFullBook;
import org.knowm.xchange.zaif.dto.marketdata.ZaifMarket;

public class ZaifMarketDataServiceRaw extends ZaifBaseService {

  protected ZaifMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public ZaifFullBook getZaifFullBook(CurrencyPair currencyPair) throws IOException {
    try {
      return this.zaif.getDepth(currencyPair.base.toString().toLowerCase(), currencyPair.counter.toString().toLowerCase());
    } catch (ZaifException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public List<ZaifMarket> getAllMarkets() {
    try {
      return this.zaif.getCurrencyPairs();
    } catch (ZaifException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public boolean checkProductExists(CurrencyPair currencyPair) {

    boolean currencyPairSupported = false;
    for (CurrencyPair cp : exchange.getExchangeSymbols()) {
      if (cp.base.getCurrencyCode().equalsIgnoreCase(currencyPair.base.getCurrencyCode())
          && cp.counter.getCurrencyCode().equalsIgnoreCase(currencyPair.counter.getCurrencyCode())) {
        currencyPairSupported = true;
        break;
      }
    }

    return currencyPairSupported;
  }
}
