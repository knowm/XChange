package com.xeiam.xchange.coinmate.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 *
 * @author Martin Stachon
 */
public class CoinmateTickerData {
    private final BigDecimal last;
    private final BigDecimal high;
    private final BigDecimal low;
    private final BigDecimal amount;
    private final BigDecimal bid;
    private final BigDecimal ask;
    
    @JsonCreator
    public CoinmateTickerData(@JsonProperty("last") BigDecimal last,
            @JsonProperty("high") BigDecimal high,
            @JsonProperty("low") BigDecimal low,
            @JsonProperty("amount") BigDecimal amount,
            @JsonProperty("bid") BigDecimal bid,
            @JsonProperty("ask") BigDecimal ask) {
        
        this.last = last;
        this.high = high;
        this.low = low;
        this.amount = amount;
        this.bid = bid;
        this.ask = ask;
    }
    
    public BigDecimal getLast() {
        return last;
    }
    
    public BigDecimal getHigh() {
        return high;
    }
    
    public BigDecimal getLow() {
        return low;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public BigDecimal getBid() {
        return bid;
    }
    
    public BigDecimal getAsk() {
        return ask;
    }
}
