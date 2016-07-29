package org.knowm.xchange.huobi.dto.streaming.request.marketdata;

/**
 * Message for subscribing to push market detail.
 */
public class MarketDetail extends AbstractPush {

  public MarketDetail(String symbolId, PushType pushType) {
    super(symbolId, pushType);
  }

}
