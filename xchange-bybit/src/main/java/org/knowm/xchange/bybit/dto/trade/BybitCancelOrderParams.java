package org.knowm.xchange.bybit.dto.trade;

import lombok.Getter;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.CancelOrderByUserReferenceParams;
import org.knowm.xchange.service.trade.params.DefaultCancelOrderByInstrumentAndIdParams;

@Getter
public class BybitCancelOrderParams extends DefaultCancelOrderByInstrumentAndIdParams
    implements CancelOrderByUserReferenceParams {

  private final String userReference;

  public BybitCancelOrderParams(Instrument instrument, String orderId, String userReference) {
    super(instrument, orderId);
    this.userReference = userReference;
  }

  @Override
  public String toString() {
    return "BybitCancelOrderParams{" +
        "instrument='" + getInstrument() + '\'' +
        ", orderId='" + getOrderId() + '\'' +
        ", userReference=" + getUserReference() +
        '}';
  }
}