package com.xeiam.xchange.coinmate.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.coinmate.dto.CoinmateBaseResponse;

/**
 *
 * @author Martin Stachon
 */
public class CoinmateTicker extends CoinmateBaseResponse<CoinmateTickerData> {

    public CoinmateTicker(@JsonProperty("error") boolean error,
            @JsonProperty("errorMessage") String errorMessage,
            @JsonProperty("data") CoinmateTickerData data) {
        
        super(error, errorMessage, data);
    }

}
