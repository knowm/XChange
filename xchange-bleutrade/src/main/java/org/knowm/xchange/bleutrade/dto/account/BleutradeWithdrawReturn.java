package org.knowm.xchange.bleutrade.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BleutradeWithdrawReturn {

    public final Boolean success;
    public final String message;
    public final String[] result;

    public BleutradeWithdrawReturn(@JsonProperty("success") Boolean success, @JsonProperty("message") String message, @JsonProperty("result") String[] result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }

    @JsonProperty("success")
    public Boolean getSuccess() {
        return success;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("result")
    public String[] getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "BleutradeWithdrawReturn{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
