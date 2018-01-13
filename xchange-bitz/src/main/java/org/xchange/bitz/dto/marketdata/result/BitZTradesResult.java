package org.xchange.bitz.dto.marketdata.result;

import org.xchange.bitz.dto.BitZResult;
import org.xchange.bitz.dto.marketdata.BitZTrades;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitZTradesResult extends BitZResult<BitZTrades> {

	public BitZTradesResult(@JsonProperty("code") int code, @JsonProperty("msg") String message, @JsonProperty("data") BitZTrades data) {
		super(code, message, data);
	}

}
