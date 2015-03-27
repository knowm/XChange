package com.xeiam.xchange.huobi.dto.streaming.response.historydata;

import com.xeiam.xchange.huobi.dto.streaming.response.ReqResponse;
import com.xeiam.xchange.huobi.dto.streaming.response.payload.ReqTimeLinePayload;

/**
 * Response of history time-interval.
 */
public class ReqTimeLineResponse extends ReqResponse<ReqTimeLinePayload> {

	public ReqTimeLineResponse(int version, String msgType, int retCode,
			String retMsg) {
		super(version, msgType, retCode, retMsg);
	}

}
