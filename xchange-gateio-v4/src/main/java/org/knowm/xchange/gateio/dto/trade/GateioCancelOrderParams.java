package org.knowm.xchange.gateio.dto.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderByInstrument;
import org.knowm.xchange.service.trade.params.CancelOrderByUserReferenceParams;

@AllArgsConstructor
@ToString
@Getter
public class GateioCancelOrderParams
    implements CancelOrderByIdParams, CancelOrderByInstrument, CancelOrderByUserReferenceParams {
  private final String orderId;
  private final Instrument instrument;
  private final String userReference;

}
