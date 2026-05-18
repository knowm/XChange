package org.knowm.xchange.dto.marketdata;

import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.instrument.Instrument;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@ToString
public class CandleStickData implements Serializable {

  @Getter
  private final Instrument instrument;
  private final List<CandleStick> candleSticks;

  public CandleStickData(Instrument instrument, List<CandleStick> candleSticks) {
    this.instrument = instrument;
    this.candleSticks = candleSticks;
  }

  public List<CandleStick> getCandleSticks() {
    return Collections.unmodifiableList(candleSticks);
  }
}
