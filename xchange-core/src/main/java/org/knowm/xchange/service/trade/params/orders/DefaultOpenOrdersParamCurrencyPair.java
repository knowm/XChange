package org.knowm.xchange.service.trade.params.orders;

import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;

public class DefaultOpenOrdersParamCurrencyPair implements OpenOrdersParamCurrencyPair {

  private CurrencyPair pair;

  public DefaultOpenOrdersParamCurrencyPair() {}

  public DefaultOpenOrdersParamCurrencyPair(CurrencyPair pair) {
    this.pair = pair;
  }

  public static List<CurrencyPair> getPairs(OpenOrdersParams params, Exchange exchange) {
    List<CurrencyPair> pairs = new ArrayList<>();
    if (params instanceof OpenOrdersParamCurrencyPair) {
      final CurrencyPair paramsCp = ((OpenOrdersParamCurrencyPair) params).getCurrencyPair();
      if (paramsCp != null) {
        pairs.add(paramsCp);
      }
    }
    if (pairs.isEmpty()) {
      pairs = exchange.getExchangeSymbols();
    }
    return pairs;
  }

  @Override
  public CurrencyPair getCurrencyPair() {

    return pair;
  }

  @Override
  public void setCurrencyPair(CurrencyPair pair) {

    this.pair = pair;
  }

  @Override
  public String toString() {
    return String.format("DefaultOpenOrdersParamCurrencyPair{%s}", pair);
  }
}
