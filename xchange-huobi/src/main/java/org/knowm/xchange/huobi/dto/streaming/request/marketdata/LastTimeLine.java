package org.knowm.xchange.huobi.dto.streaming.request.marketdata;

/**
 * Message for subscribing to push the last time-interval data.
 */
public class LastTimeLine extends AbstractPush {

  public LastTimeLine(String symbolId, PushType pushType) {
    super(symbolId, pushType);
  }

}
