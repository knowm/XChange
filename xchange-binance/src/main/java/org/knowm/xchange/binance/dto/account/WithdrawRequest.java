package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class WithdrawRequest extends WapiResponse<Void> {
    
    
    public WithdrawRequest(@JsonProperty("success") boolean success, @JsonProperty("msg") String msg) {
        super(success, msg);
    }
    
    @Override
    public Void getData() {
        return null;
    }
}