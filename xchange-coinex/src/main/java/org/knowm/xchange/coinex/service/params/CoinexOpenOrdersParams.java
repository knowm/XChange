package org.knowm.xchange.coinex.service.params;

import lombok.Builder;
import lombok.Data;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParam;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamInstrument;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamLimit;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamOffset;

@Data
@Builder
public class CoinexOpenOrdersParams extends DefaultOpenOrdersParam implements OpenOrdersParamLimit,
    OpenOrdersParamOffset, OpenOrdersParamInstrument {

  public static final Integer DEFAULT_LIMIT = 1000;

  private Instrument instrument;

  private Integer limit;

  private Integer offset;
}
