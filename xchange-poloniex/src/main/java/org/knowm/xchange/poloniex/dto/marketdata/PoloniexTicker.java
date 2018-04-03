package org.knowm.xchange.poloniex.dto.marketdata;

import org.knowm.xchange.currency.CurrencyPair;

/** @author Zach Holmes */
public class PoloniexTicker {

  private PoloniexMarketData poloniexMarketData;
  private CurrencyPair currencyPair;

  public PoloniexTicker(PoloniexMarketData poloniexMarketData, CurrencyPair currencyPair) {

    super();
    this.poloniexMarketData = poloniexMarketData;
    this.currencyPair = currencyPair;
  }

  public PoloniexMarketData getPoloniexMarketData() {

    return poloniexMarketData;
  }

  public CurrencyPair getCurrencyPair() {

    return currencyPair;
  }
}
