package org.knowm.xchange.service.trade.params;

import java.util.Collection;
import java.util.Collections;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.knowm.xchange.instrument.Instrument;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DefaultTradeHistoryParamMultiInstrument implements TradeHistoryParamMultiInstrument {

  @Builder.Default private Collection<Instrument> instruments = Collections.emptySet();
}
