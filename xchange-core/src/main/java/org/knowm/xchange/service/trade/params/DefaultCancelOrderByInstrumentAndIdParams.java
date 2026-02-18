package org.knowm.xchange.service.trade.params;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.knowm.xchange.instrument.Instrument;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DefaultCancelOrderByInstrumentAndIdParams
    implements CancelOrderByIdParams, CancelOrderByInstrument {
  private Instrument instrument;
  private String orderId;

  public void setInstrument(Instrument instrument) {
    this.instrument = instrument;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  @Override
  public String getOrderId() {
    return orderId;
  }

  @Override
  public Instrument getInstrument() {
    return instrument;
  }
}
