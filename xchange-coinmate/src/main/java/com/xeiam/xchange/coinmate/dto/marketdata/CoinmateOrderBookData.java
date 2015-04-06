package com.xeiam.xchange.coinmate.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 *
 * @author Martin Stachon
 */
public class CoinmateOrderBookData {

    private final List<CoinmateOrderBookEntry> asks;
    private final List<CoinmateOrderBookEntry> bids;
    
    public CoinmateOrderBookData(@JsonProperty("asks") List<CoinmateOrderBookEntry> asks,
            @JsonProperty("bids") List<CoinmateOrderBookEntry> bids) {
        
        this.asks = asks;
        this.bids = bids;
    }
    
    public List<CoinmateOrderBookEntry> getAsks() {
        return asks;
    }
    
    public List<CoinmateOrderBookEntry> getBids() {
        return bids;
    }
    
}
