package org.knowm.xchange.huobi.dto.streaming.response.marketdata;

import org.knowm.xchange.huobi.dto.streaming.response.marketdata.payload.MarketDepthTopDiffPayload;

/**
 * Push of top market-depth difference.
 */
public class MarketDepthTopDiff extends Message<MarketDepthTopDiffPayload> {

  public MarketDepthTopDiff(int version, String msgType, String symbolId, MarketDepthTopDiffPayload payload) {
    super(version, msgType, symbolId, payload);
  }

}
