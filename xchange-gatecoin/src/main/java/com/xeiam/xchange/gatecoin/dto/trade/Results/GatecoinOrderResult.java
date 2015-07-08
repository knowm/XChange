
package com.xeiam.xchange.gatecoin.dto.trade.Results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.gatecoin.dto.marketdata.Response;
import com.xeiam.xchange.gatecoin.dto.trade.GatecoinOrder;

/**
 *
 * @author sumedha
 */
public class GatecoinOrderResult {
    
    private final GatecoinOrder[] orders;
    private final Response responseStatus;

    @JsonCreator
    public GatecoinOrderResult(@JsonProperty("orders") GatecoinOrder[] orders,@JsonProperty("responseStatus") Response responseStatus) {
        this.orders = orders;
        this.responseStatus = responseStatus;
    }

    public GatecoinOrder[] getOrders() {
        return orders;
    }

    public Response getResponseStatus() {
        return responseStatus;
    }
}
