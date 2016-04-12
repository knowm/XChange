package org.knowm.xchange.huobi.dto.streaming.response.marketdata;

import org.knowm.xchange.huobi.dto.streaming.response.marketdata.payload.LastKLinePayload;

/**
 * Push of the last data details from candlestick chart.
 */
public class LastKLine extends Message<LastKLinePayload> {

  public LastKLine(int version, String msgType, String symbolId, LastKLinePayload payload) {
    super(version, msgType, symbolId, payload);
  }

}
