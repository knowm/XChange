package org.knowm.xchange.huobi.dto.streaming.response.marketdata;

import org.knowm.xchange.huobi.dto.streaming.response.marketdata.payload.LastTimeLinePayload;

/**
 * Push of the last time-interval data.
 */
public class LastTimeLine extends Message<LastTimeLinePayload> {

  public LastTimeLine(int version, String msgType, String symbolId, LastTimeLinePayload payload) {
    super(version, msgType, symbolId, payload);
  }

}
