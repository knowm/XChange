package org.knowm.xchange.ftx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.ftx.dto.FtxResponse;

public class FtxSubAccountTransferResponse extends FtxResponse<FtxSubAccountTranferDto> {

    public FtxSubAccountTransferResponse(
        @JsonProperty("success") boolean success,
        @JsonProperty("result") FtxSubAccountTranferDto result) {
        super(success, result);
    }
}
