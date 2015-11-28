package com.xeiam.xchange.okcoin.service.streaming;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.okcoin.FuturesContract;

class FuturesChannelProvider implements ChannelProvider {
  private final String contractName;

  FuturesChannelProvider(FuturesContract contract) {
    contractName = contract.getName();
  }

  private static String pairToString(CurrencyPair currencyPair) {
    return currencyPair.base.getCurrencyCode().toLowerCase() + currencyPair.counter.getCurrencyCode().toLowerCase();
  }

  @Override
  public String getTicker(CurrencyPair currencyPair) {
    return "ok_" + pairToString(currencyPair) + "_future_ticker_" + contractName;
  }

  @Override
  public String getDepth(CurrencyPair currencyPair) {
    return "ok_" + pairToString(currencyPair) + "_future_depth_" + contractName;
  }

  @Override
  public String getTrades(CurrencyPair currencyPair) {
    return "ok_" + pairToString(currencyPair) + "_future_trade_v1_" + contractName;
  }
}