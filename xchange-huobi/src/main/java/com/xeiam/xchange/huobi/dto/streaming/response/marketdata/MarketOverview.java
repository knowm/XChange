package com.xeiam.xchange.huobi.dto.streaming.response.marketdata;

import com.xeiam.xchange.huobi.dto.streaming.response.marketdata.payload.MarketOverviewPayload;

/**
 * Push of the market overview data.
 */
public class MarketOverview extends Message<MarketOverviewPayload> {

	public MarketOverview(int version, String msgType, String symbolId,
			MarketOverviewPayload payload) {
		super(version, msgType, symbolId, payload);
	}

}
