package com.xeiam.xchange.gatecoin.dto.trade.Results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.gatecoin.dto.marketdata.Response;

/**
 *
 * @author sumedha
 */
public class GatecoinPlaceOrderResult {
    private final String clOrderId;
    private final Response responseStatus;

    @JsonCreator
    public GatecoinPlaceOrderResult(@JsonProperty("clOrderId") String clOrderId,@JsonProperty("responseStatus") Response responseStatus) {
        this.clOrderId = clOrderId;
        this.responseStatus = responseStatus;
    }

    public String getOrderId() {
        return clOrderId;
    }

    public Response getResponseStatus() {
        return responseStatus;
    }
}
