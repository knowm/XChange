package org.knowm.xchange.coinmate.dto.trade;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamInstrument;

public class CoinmateTradeHistoryParam implements TradeHistoryParamInstrument {

  Instrument pair;
  int limit = 1000;

  public CoinmateTradeHistoryParam(CurrencyPair pair) {
    this.pair = pair;
  }

  public CoinmateTradeHistoryParam(CurrencyPair pair, int limit) {
    this.pair = pair;
    this.limit = limit;
  }

  @Override
  public Instrument getInstrument() {
    return pair;
  }

  @Override
  public void setInstrument(Instrument pair) {
    this.pair = pair;
  }
}
