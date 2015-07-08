
package com.xeiam.xchange.gatecoin.dto.account.Results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.gatecoin.dto.marketdata.Response;

/**
 *
 * @author sumedha
 */
public class GatecoinWithdrawResult {
    
     private final Response responseStatus;

    @JsonCreator
    public GatecoinWithdrawResult(@JsonProperty("responseStatus") Response responseStatus) {
       
        this.responseStatus = responseStatus;
    }   

    public Response getResponseStatus() {
        return responseStatus;
    }
}
