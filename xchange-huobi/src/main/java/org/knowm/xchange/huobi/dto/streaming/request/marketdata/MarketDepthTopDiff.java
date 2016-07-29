package org.knowm.xchange.huobi.dto.streaming.request.marketdata;

/**
 * Message for subscribing to push top market-depth difference.
 */
public class MarketDepthTopDiff extends AbstractPush {

  public MarketDepthTopDiff(String symbolId, PushType pushType) {
    super(symbolId, pushType);
  }

}
