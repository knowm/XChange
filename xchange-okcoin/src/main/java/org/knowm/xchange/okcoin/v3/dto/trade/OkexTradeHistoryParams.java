package org.knowm.xchange.okcoin.v3.dto.trade;

import lombok.Data;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamInstrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

@Data
public class OkexTradeHistoryParams implements TradeHistoryParams, TradeHistoryParamInstrument {

  private Instrument instrument;

  /** provide an order id, so the result will contain only the orders which are newer */
  private String sinceOrderId;

  @Override
  public Instrument getInstrument() {

    return instrument;
  }

  @Override
  public void setInstrument(Instrument instrument) {
    this.instrument = instrument;
  }
}
