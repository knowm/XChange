package org.knowm.xchange.huobi.dto.streaming.request.marketdata;

import org.knowm.xchange.huobi.dto.streaming.dto.Percent;

/**
 * Message for subscribing to push market-depth difference.
 */
public class MarketDepthDiff extends AbstractPush {

  private final Percent percent;

  public MarketDepthDiff(String symbolId, PushType pushType, Percent percent) {
    super(symbolId, pushType);
    this.percent = percent;
  }

  public Percent getPercent() {
    return percent;
  }

}
