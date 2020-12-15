package org.knowm.xchange.ftx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FtxResponse<V> {

    private final boolean success;
    private final V result;

    public FtxResponse(
        @JsonProperty("success") boolean success,
        @JsonProperty("result") V result) {
        this.success = success;
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public V getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "FtxResponse{" +
            "success=" + success +
            ", result=" + result +
            '}';
    }
}
