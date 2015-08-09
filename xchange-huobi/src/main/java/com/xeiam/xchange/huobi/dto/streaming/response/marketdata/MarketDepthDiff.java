package com.xeiam.xchange.huobi.dto.streaming.response.marketdata;

import com.xeiam.xchange.huobi.dto.streaming.response.marketdata.payload.MarketDepthDiffPayload;

/**
 * Push of market-depth difference.
 */
public class MarketDepthDiff extends Message<MarketDepthDiffPayload> {

  public MarketDepthDiff(int version, String msgType, String symbolId, MarketDepthDiffPayload payload) {
    super(version, msgType, symbolId, payload);
  }

}
