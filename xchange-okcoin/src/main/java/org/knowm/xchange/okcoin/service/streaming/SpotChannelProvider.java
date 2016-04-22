package org.knowm.xchange.okcoin.service.streaming;

import org.knowm.xchange.currency.CurrencyPair;

class SpotChannelProvider implements ChannelProvider {

  private static String pairToString(CurrencyPair currencyPair) {
    return currencyPair.base.getCurrencyCode().toLowerCase() + currencyPair.counter.getCurrencyCode().toLowerCase();
  }

  @Override
  public String getTicker(CurrencyPair currencyPair) {
    return "ok_" + pairToString(currencyPair) + "_ticker";
  }

  @Override
  public String getDepth(CurrencyPair currencyPair) {
    return "ok_" + pairToString(currencyPair) + "_depth";
  }

  @Override
  public String getTrades(CurrencyPair currencyPair) {
    return "ok_" + pairToString(currencyPair) + "_trades_v1";
  }
}