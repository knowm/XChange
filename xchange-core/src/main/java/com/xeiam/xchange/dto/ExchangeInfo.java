package com.xeiam.xchange.dto;

import com.xeiam.xchange.currency.CurrencyPair;

import java.util.List;

/**
 * Author: brox
 *
 * Returns public info about exchange, such as allowed currency pairs, fees etc.
 */
// TODO: implement me
public class ExchangeInfo {

  private final List<CurrencyPair> pairs;


  public ExchangeInfo(List<CurrencyPair> pairs) {

    this.pairs = pairs;
  }

  @Override
  public String toString() {

    return "ExchangeInfo [pairs=" + pairs + "]";
  }

}
