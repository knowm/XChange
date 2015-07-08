
package com.xeiam.xchange.gatecoin.dto.trade.Results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.gatecoin.dto.marketdata.Response;

/**
 *
 * @author sumedha
 */
public class GatecoinCancelOrderResult {
  
    private final Response responseStatus;

    @JsonCreator
    public GatecoinCancelOrderResult(@JsonProperty("responseStatus") Response responseStatus) {
       
        this.responseStatus = responseStatus;
    }

   

    public Response getResponseStatus() {
        return responseStatus;
    }
}
