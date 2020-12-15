package org.knowm.xchange.ftx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.ftx.dto.FtxResponse;

public class FtxAccountResponse extends FtxResponse<FtxAccountDto> {

    public FtxAccountResponse(
        @JsonProperty("success") boolean success,
        @JsonProperty("result") FtxAccountDto result) {
        super(success, result);
    }
}
