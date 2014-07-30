package com.xeiam.xchange.bitbay.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitbay.Bitbay;
import com.xeiam.xchange.bitbay.dto.marketdata.BitbayOrderBook;
import com.xeiam.xchange.bitbay.dto.marketdata.BitbayTicker;
import com.xeiam.xchange.bitbay.dto.marketdata.BitbayTrade;
import com.xeiam.xchange.currency.CurrencyPair;

/**
 * @author kpysniak
 */
public class BitbayMarketDataServiceRaw extends BitbayBasePollingService {

  private final Bitbay bitbay;

  /**
   * Constructor Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  protected BitbayMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.bitbay = RestProxyFactory.createProxy(Bitbay.class, exchangeSpecification.getSslUri());
  }

  public BitbayTicker getBitbayTicker(CurrencyPair currencyPair) throws IOException {

    return bitbay.getBitbayTicker(currencyPair.baseSymbol.toUpperCase() + currencyPair.counterSymbol.toString());
  }

  public BitbayOrderBook getBitbayOrderBook(CurrencyPair currencyPair) throws IOException {

    return bitbay.getBitbayOrderBook(currencyPair.baseSymbol.toUpperCase() + currencyPair.counterSymbol.toString());
  }

  public BitbayTrade[] getBitbayTrades(CurrencyPair currencyPair) throws IOException {

    return bitbay.getBitbayTrades(currencyPair.baseSymbol.toUpperCase() + currencyPair.counterSymbol.toString());
  }

}
