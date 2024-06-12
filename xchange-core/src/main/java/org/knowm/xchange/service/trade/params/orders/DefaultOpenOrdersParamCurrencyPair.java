package org.knowm.xchange.service.trade.params.orders;

import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;

public class DefaultOpenOrdersParamCurrencyPair implements OpenOrdersParamCurrencyPair {

  private CurrencyPair pair;

  public DefaultOpenOrdersParamCurrencyPair() {}

  public DefaultOpenOrdersParamCurrencyPair(CurrencyPair pair) {
    this.pair = pair;
  }

  public static List<Instrument> getPairs(OpenOrdersParams params, Exchange exchange) {
    List<Instrument> pairs = new ArrayList<>();
    if (params instanceof OpenOrdersParamCurrencyPair) {
      final CurrencyPair paramsCp = ((OpenOrdersParamCurrencyPair) params).getCurrencyPair();
      if (paramsCp != null) {
        pairs.add(paramsCp);
      }
    }
    if (pairs.isEmpty()) {
      pairs = exchange.getExchangeInstruments();
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
