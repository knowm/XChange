package org.knowm.xchange.btcmarkets.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.btcmarkets.dto.BTCMarketsBaseResponse;

public class BTCMarketsWithdrawCryptoResponse extends BTCMarketsBaseResponse {
    public final String status;

    public BTCMarketsWithdrawCryptoResponse(Boolean success, String errorMessage, Integer errorCode, @JsonProperty("status") String status) {
        super(success, errorMessage, errorCode);
        this.status = status;
    }
}
