package org.knowm.xchange.ftx.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.ftx.dto.FtxResponse;

public class FtxOrderbookResponse extends FtxResponse<FtxOrderbookDto> {

    public FtxOrderbookResponse(
        @JsonProperty("success") boolean success,
        @JsonProperty("result") FtxOrderbookDto result) {
        super(success, result);
    }
}
