
package com.xeiam.xchange.gatecoin.dto.account.Results;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.gatecoin.dto.account.GatecoinDepositAddress;
import com.xeiam.xchange.gatecoin.dto.marketdata.Response;

/**
 *
 * @author sumedha
 */
public class GatecoinDepositAddressResult
{
    private final GatecoinDepositAddress[]  addresses;
     private final Response responseStatus; 

    public GatecoinDepositAddressResult(@JsonProperty("addresses") GatecoinDepositAddress[] addresses,@JsonProperty("responseStatus") Response responseStatus) {
        this.addresses = addresses;
        this.responseStatus = responseStatus;
    }

    public GatecoinDepositAddress[] getAddresses() {
        return addresses;
    }

    public Response getResponseStatus() {
        return responseStatus;
    }
    
}
