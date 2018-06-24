package org.knowm.xchange.bitbay.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitbay.dto.marketdata.BitbayOrderBook;
import org.knowm.xchange.bitbay.dto.marketdata.BitbayTicker;
import org.knowm.xchange.bitbay.dto.marketdata.BitbayTrade;
import org.knowm.xchange.currency.CurrencyPair;

/** @author kpysniak */
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

    return bitbay.getBitbayTicker(
        currencyPair.base.getCurrencyCode().toUpperCase() + currencyPair.counter.getCurrencyCode());
  }

  public BitbayOrderBook getBitbayOrderBook(CurrencyPair currencyPair) throws IOException {

    return bitbay.getBitbayOrderBook(
        currencyPair.base.getCurrencyCode().toUpperCase() + currencyPair.counter.getCurrencyCode());
  }

  public BitbayTrade[] getBitbayTrades(CurrencyPair currencyPair, Object[] args)
      throws IOException {
    long since = 0;
    if (args.length >= 1 && args[0] != null) {
      since = ((Number) args[0]).longValue();
    }
    String sort = "asc";
    if (args.length >= 2) {
      sort = (String) args[1];
    }
    int limit = 50; // param works up to 150
    if (args.length == 3) {
      limit = ((Number) args[2]).intValue();
    }
    return bitbay.getBitbayTrades(
        currencyPair.base.getCurrencyCode().toUpperCase() + currencyPair.counter.getCurrencyCode(),
        since,
        sort,
        limit);
  }
}
