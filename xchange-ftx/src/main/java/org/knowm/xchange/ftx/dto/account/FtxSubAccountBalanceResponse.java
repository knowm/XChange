package org.knowm.xchange.ftx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.ftx.dto.FtxResponse;

public class FtxSubAccountBalanceResponse extends FtxResponse<FtxSubAccountBalancesDto> {

    public FtxSubAccountBalanceResponse(
        @JsonProperty("success") boolean success,
        @JsonProperty("result") FtxSubAccountBalancesDto result) {
        super(success, result);
    }
}
