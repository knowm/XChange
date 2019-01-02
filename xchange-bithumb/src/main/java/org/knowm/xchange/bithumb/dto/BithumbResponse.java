package org.knowm.xchange.bithumb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BithumbResponse<D> {

    private final String status;
    private final D data;

    public BithumbResponse(
            @JsonProperty("status") String status,
            @JsonProperty("data") D data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public D getData() {
        return data;
    }
}
