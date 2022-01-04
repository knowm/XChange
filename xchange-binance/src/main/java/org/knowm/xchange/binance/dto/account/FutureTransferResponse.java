package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FutureTransferResponse {
    public final long tranId;

    public FutureTransferResponse(@JsonProperty("tranId") long tranId) {
        this.tranId = tranId;
    }

    public long getTranId() {
        return tranId;
    }
}
