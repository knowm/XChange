package org.knowm.xchange.bitbay.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitbay.dto.marketdata.BitbayOrderBook;
import org.knowm.xchange.bitbay.dto.marketdata.BitbayTicker;
import org.knowm.xchange.bitbay.dto.marketdata.BitbayTrade;
import org.knowm.xchange.currency.CurrencyPair;

/**
 * @author kpysniak
 */
public class BitbayMarketDataServiceRaw extends BitbayBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BitbayMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public BitbayTicker getBitbayTicker(CurrencyPair currencyPair) throws IOException {

    return bitbay.getBitbayTicker(currencyPair.base.getCurrencyCode().toUpperCase() + currencyPair.counter.getCurrencyCode().toString());
  }

  public BitbayOrderBook getBitbayOrderBook(CurrencyPair currencyPair) throws IOException {

    return bitbay.getBitbayOrderBook(currencyPair.base.getCurrencyCode().toUpperCase() + currencyPair.counter.getCurrencyCode().toString());
  }

  public BitbayTrade[] getBitbayTrades(CurrencyPair currencyPair, Object[] args) throws IOException {
    long since = 0;
    if (args.length == 1) {
      since = ((Number) args[0]).longValue();
    }
    return bitbay.getBitbayTrades(currencyPair.base.getCurrencyCode().toUpperCase() + currencyPair.counter.getCurrencyCode().toString(), since);
  }

}
