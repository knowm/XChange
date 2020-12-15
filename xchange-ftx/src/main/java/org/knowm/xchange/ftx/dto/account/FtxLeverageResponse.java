package org.knowm.xchange.ftx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.ftx.dto.FtxResponse;

public class FtxLeverageResponse extends FtxResponse<FtxLeverageDto> {

    public FtxLeverageResponse(
        @JsonProperty("success") boolean success,
        @JsonProperty("result") FtxLeverageDto result) {
        super(success, result);
    }
}
