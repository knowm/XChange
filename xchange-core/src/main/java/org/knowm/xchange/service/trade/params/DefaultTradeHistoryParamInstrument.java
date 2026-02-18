package org.knowm.xchange.service.trade.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.knowm.xchange.instrument.Instrument;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DefaultTradeHistoryParamInstrument implements TradeHistoryParamInstrument {

  private Instrument instrument;
}
