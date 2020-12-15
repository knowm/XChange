package org.knowm.xchange.ftx.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.ftx.dto.FtxResponse;

public class FtxOrderResponse extends FtxResponse<FtxOrderDto> {

    public FtxOrderResponse(
        @JsonProperty("success") boolean success,
        @JsonProperty("result") FtxOrderDto result) {
        super(success, result);
    }
}
