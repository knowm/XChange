package org.knowm.xchange.bybit.dto.trade;

import lombok.Getter;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamInstrument;

@Getter
public class BybitOpenOrdersParam extends DefaultOpenOrdersParamInstrument {
  private final BybitCategory category;

  public BybitOpenOrdersParam(Instrument instrument,  BybitCategory category) {
    super(instrument);
    this.category = category;
  }
  @Override
  public String toString() {
    return "BybitOrderQueryParams{" +
        "category='" + category + '\'' +
        ", instrument='" + getInstrument() + '\'' +
        '}';
  }
}
