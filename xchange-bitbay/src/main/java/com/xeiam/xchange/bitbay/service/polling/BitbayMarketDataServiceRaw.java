package com.xeiam.xchange.bitbay.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitbay.Bitbay;
import com.xeiam.xchange.bitbay.dto.marketdata.BitbayOrderBook;
import com.xeiam.xchange.bitbay.dto.marketdata.BitbayTicker;
import com.xeiam.xchange.bitbay.dto.marketdata.BitbayTrade;
import com.xeiam.xchange.currency.CurrencyPair;

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
