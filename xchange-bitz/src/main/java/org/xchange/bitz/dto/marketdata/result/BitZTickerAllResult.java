package org.xchange.bitz.dto.marketdata.result;

import org.xchange.bitz.dto.BitZResult;
import org.xchange.bitz.dto.marketdata.BitZTickerAll;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitZTickerAllResult extends BitZResult<BitZTickerAll> {

	public BitZTickerAllResult(@JsonProperty("code") int code, @JsonProperty("msg") String message, @JsonProperty("data") BitZTickerAll data) {
		super(code, message, data);
	}
}
