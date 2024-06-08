package org.knowm.xchange.coinex.service.params;

import lombok.Builder;
import lombok.Data;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.params.Params;

@Data
@Builder
public class CoinexOrderBookParams implements Params {

  public static final Integer DEFAULT_LIMIT = 50;
  public static final Integer DEFAULT_INTERVAL = 0;

  private Instrument instrument;

  private Integer limit;

  private Integer interval;

}
