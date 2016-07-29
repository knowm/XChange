package org.knowm.xchange.huobi.dto.streaming.request.marketdata;

/**
 * Message for subscribing to push the market overview data.
 */
public class MarketOverview extends AbstractPush {

  public MarketOverview(String symbolId, PushType pushType) {
    super(symbolId, pushType);
  }

}
