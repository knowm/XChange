package org.knowm.xchange.ftx.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.ftx.dto.FtxResponse;

public class FtxMarketsResponse extends FtxResponse<FtxMarketsDto> {

    public FtxMarketsResponse(
        @JsonProperty("success") boolean success,
        @JsonProperty("result") FtxMarketsDto result) {
        super(success, result);
    }
}
