package com.xeiam.xchange.coinmate.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.coinmate.dto.CoinmateBaseResponse;

/**
 *
 * @author Martin Stachon
 */
public class CoinmateTransactions extends CoinmateBaseResponse<CoinmateTransactionsData>{

    public CoinmateTransactions(@JsonProperty("error") boolean error,
            @JsonProperty("errorMessage") String errorMessage,
            @JsonProperty("data") CoinmateTransactionsData data) {
        
        super(error, errorMessage, data);
    }

}
