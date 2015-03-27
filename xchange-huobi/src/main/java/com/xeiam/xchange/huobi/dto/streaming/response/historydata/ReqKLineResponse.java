package com.xeiam.xchange.huobi.dto.streaming.response.historydata;

import com.xeiam.xchange.huobi.dto.streaming.response.ReqResponse;
import com.xeiam.xchange.huobi.dto.streaming.response.payload.ReqKLinePayload;

/**
 * Response of history candlestick data.
 */
public class ReqKLineResponse extends ReqResponse<ReqKLinePayload> {

	public ReqKLineResponse(int version, String msgType, int retCode,
			String retMsg) {
		super(version, msgType, retCode, retMsg);
	}

}
