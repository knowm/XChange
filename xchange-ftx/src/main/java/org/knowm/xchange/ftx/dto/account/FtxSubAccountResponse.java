package org.knowm.xchange.ftx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.ftx.dto.FtxResponse;

public class FtxSubAccountResponse extends FtxResponse<FtxSubAccountDto> {

    public FtxSubAccountResponse(
        @JsonProperty("success") boolean success,
        @JsonProperty("result") FtxSubAccountDto result) {
        super(success, result);
    }
}
