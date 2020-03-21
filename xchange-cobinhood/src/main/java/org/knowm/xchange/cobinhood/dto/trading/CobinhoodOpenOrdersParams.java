package org.knowm.xchange.cobinhood.dto.trading;

import org.knowm.xchange.cobinhood.CobinhoodAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamInstrument;

public class CobinhoodOpenOrdersParams extends DefaultOpenOrdersParamInstrument {

  private final Integer limit;

  public CobinhoodOpenOrdersParams(CurrencyPair pair, Integer limit) {
    super(pair);
    this.limit = limit;
  }

  public CobinhoodOpenOrdersParams(CurrencyPair pair) {
    this(pair, null);
  }

  public Integer getLimit() {
    return limit;
  }

  public String getPairId() {
    CurrencyPair pair = getCurrencyPair();
    return pair == null ? null : CobinhoodAdapters.adaptCurrencyPair(pair);
  }
}
