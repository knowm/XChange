package org.knowm.xchange.service.trade.params;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;

public interface TradeHistoryParamInstrument extends TradeHistoryParams {

  Instrument getInstrument();

  void setInstrument(Instrument instrument);

  default CurrencyPair getCurrencyPair() {
    if (getInstrument() instanceof CurrencyPair) return (CurrencyPair) getInstrument();
    return null;
  }

  default void setCurrencyPair(CurrencyPair pair) {
    setInstrument(pair);
  }
}
