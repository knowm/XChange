package org.knowm.xchange.okex.dto.trade;

import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderByInstrument;
import org.knowm.xchange.service.trade.params.CancelOrderByUserReferenceParams;

/** Author: Max Gao (gaamox@tutanota.com) Created: 10-06-2021 */
public class OkexTradeParams {
  @Getter
  @ToString
  public static class OkexCancelOrderParams
      implements CancelOrderByIdParams, CancelOrderByInstrument, CancelOrderByUserReferenceParams {
    public final Instrument instrument;
    public final String orderId;
    public final String userReference;

    public OkexCancelOrderParams(Instrument instrument, String orderId, String userReference) {
      this.instrument = instrument;
      this.orderId = orderId;
      this.userReference = userReference;
    }

    public OkexCancelOrderParams(Instrument instrument, String orderId) {
      this.instrument = instrument;
      this.orderId = orderId;
      this.userReference = null;
    }
  }
}
