package org.knowm.xchange.okex.v5.dto.trade;

import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderByInstrument;

/** Author: Max Gao (gaamox@tutanota.com) Created: 10-06-2021 */
public class OkexTradeParams {
  public static class OkexCancelOrderParams
      implements CancelOrderByIdParams, CancelOrderByInstrument {
    public final Instrument instrument;
    public final String orderId;

    public OkexCancelOrderParams(Instrument instrument, String orderId) {
      this.instrument = instrument;
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
}
