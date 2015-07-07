package com.xeiam.xchange.gatecoin.dto.marketdata.Results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.gatecoin.dto.marketdata.GatecoinTicker;
import com.xeiam.xchange.gatecoin.dto.marketdata.Response;

/**
 *
 * @author sumdeha
 */
public class GatecoinTickerResult {
    
    private final GatecoinTicker[] tickers;
    private final Response responseStatus;
    
    @JsonCreator
    public GatecoinTickerResult(@JsonProperty("tickers") GatecoinTicker[] tickers, @JsonProperty ("responseStatus") Response responseStatus)
    {
        this.tickers = tickers;
        this.responseStatus = responseStatus;
    }
    public GatecoinTicker[] getTicker() 
    {
        return tickers;
    }
    
     public Response getResponse() 
    {
        return responseStatus;
    }
}
