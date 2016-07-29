package org.knowm.xchange.huobi.dto.streaming.request.marketdata;

import org.knowm.xchange.huobi.dto.streaming.dto.Period;

/**
 * Message for subscribing to push the last data details from candlestick chart.
 */
public class LastKLine extends AbstractPush {

  private final Period period;

  public LastKLine(String symbolId, PushType pushType, Period period) {
    super(symbolId, pushType);
    this.period = period;
  }

  public Period getPeriod() {
    return period;
  }

}
