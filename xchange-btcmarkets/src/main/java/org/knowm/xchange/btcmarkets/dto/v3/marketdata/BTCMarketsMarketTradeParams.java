package org.knowm.xchange.btcmarkets.dto.v3.marketdata;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.params.Params;

public class BTCMarketsMarketTradeParams implements Params {
  public Integer limit;
  public Long before;
  public Long after;
  public CurrencyPair currencyPair;

  public BTCMarketsMarketTradeParams(
      Instrument currencyPair, Integer limit, Long before, Long after) {
    super();
    this.limit = limit;
    this.before = before;
    this.after = after;
    this.currencyPair = (CurrencyPair) currencyPair;
  }

  @Override
  public String toString() {

    return String.format(
        "BTCMarketsMarketTradeParams: {limit: %s, before: %s, after: %s, CurrencyPair: %s}",
        limit, before, after, currencyPair);
  }
}
