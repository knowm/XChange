package com.xeiam.xchange.coinmate.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.coinmate.dto.CoinmateBaseResponse;

/**
 *
 * @author Martin Stachon
 */
public class CoinmateOrderBook extends CoinmateBaseResponse<CoinmateOrderBookData> {

    public CoinmateOrderBook(@JsonProperty("error") boolean error,
            @JsonProperty("errorMessage") String errorMessage,
            @JsonProperty("data") CoinmateOrderBookData data) {
        
        super(error, errorMessage, data);
    }

}
