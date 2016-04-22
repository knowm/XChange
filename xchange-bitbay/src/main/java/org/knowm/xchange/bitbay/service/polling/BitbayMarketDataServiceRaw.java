package org.knowm.xchange.bitbay.service.polling;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitbay.Bitbay;
import org.knowm.xchange.bitbay.dto.marketdata.BitbayOrderBook;
import org.knowm.xchange.bitbay.dto.marketdata.BitbayTicker;
import org.knowm.xchange.bitbay.dto.marketdata.BitbayTrade;
import org.knowm.xchange.currency.CurrencyPair;

import si.mazi.rescu.RestProxyFactory;

/**
 * @author kpysniak
 */
public class BitbayMarketDataServiceRaw extends BitbayBasePollingService {

  private final Bitbay bitbay;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BitbayMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.bitbay = RestProxyFactory.createProxy(Bitbay.class, exchange.getExchangeSpecification().getSslUri());
  }

  public BitbayTicker getBitbayTicker(CurrencyPair currencyPair) throws IOException {

    return bitbay.getBitbayTicker(currencyPair.base.getCurrencyCode().toUpperCase() + currencyPair.counter.getCurrencyCode().toString());
  }

  public BitbayOrderBook getBitbayOrderBook(CurrencyPair currencyPair) throws IOException {

    return bitbay.getBitbayOrderBook(currencyPair.base.getCurrencyCode().toUpperCase() + currencyPair.counter.getCurrencyCode().toString());
  }

  public BitbayTrade[] getBitbayTrades(CurrencyPair currencyPair) throws IOException {

    return bitbay.getBitbayTrades(currencyPair.base.getCurrencyCode().toUpperCase() + currencyPair.counter.getCurrencyCode().toString());
  }

}
