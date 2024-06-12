package org.knowm.xchange.dto.marketdata;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import org.knowm.xchange.instrument.Instrument;

public class CandleStickData implements Serializable {

  private final Instrument instrument;
  private final List<CandleStick> candleSticks;

  public CandleStickData(Instrument instrument, List<CandleStick> candleSticks) {
    this.instrument = instrument;
    this.candleSticks = candleSticks;
  }

  public Instrument getInstrument() {
    return instrument;
  }

  public List<CandleStick> getCandleSticks() {
    return Collections.unmodifiableList(candleSticks);
  }
}
