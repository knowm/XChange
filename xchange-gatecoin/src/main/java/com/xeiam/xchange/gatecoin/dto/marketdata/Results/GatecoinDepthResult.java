
package com.xeiam.xchange.gatecoin.dto.marketdata.Results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.gatecoin.dto.marketdata.GatecoinDepth;
import com.xeiam.xchange.gatecoin.dto.marketdata.Response;

/**
 *
 * @author sumdeha
 */
public class GatecoinDepthResult {
    
    private final GatecoinDepth[] asks;
    private final GatecoinDepth[] bids;
    private final Response responseStatus;
    
    @JsonCreator
    public GatecoinDepthResult(@JsonProperty("asks") GatecoinDepth[] asks,@JsonProperty("bids") GatecoinDepth[] bids, @JsonProperty ("responseStatus") Response responseStatus)
    {
        this.asks = asks;
        this.bids = bids;
        this.responseStatus = responseStatus;
    }
    public GatecoinDepth[] getAsks() 
    {
        return this.asks;
    }
    
    public GatecoinDepth[] getBids() 
    {
        return this.bids;
    }
    
     public Response getResponse() 
    {
        return responseStatus;
    }
}
