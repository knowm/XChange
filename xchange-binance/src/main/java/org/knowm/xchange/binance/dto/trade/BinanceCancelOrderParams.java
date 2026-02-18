package org.knowm.xchange.binance.dto.trade;

import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderByInstrument;
import org.knowm.xchange.service.trade.params.CancelOrderByUserReferenceParams;

@Getter
@ToString
public class BinanceCancelOrderParams
    implements CancelOrderByIdParams, CancelOrderByInstrument, CancelOrderByUserReferenceParams {

  private final String orderId;
  private final Instrument instrument;
  private final String userReference;

  public BinanceCancelOrderParams(Instrument instrument, String orderId, String userReference) {
    this.instrument = instrument;
    this.orderId = orderId;
    if (userReference != null && !userReference.isEmpty()) {
      this.userReference = userReference;
    } else this.userReference = null;
  }
}
