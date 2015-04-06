package com.xeiam.xchange.coinmate.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 *
 * @author Martin Stachon
 */
public class CoinmateOrderBookEntry {
    private final BigDecimal price;
    private final BigDecimal amount;
    
    public CoinmateOrderBookEntry(@JsonProperty("price") BigDecimal price,
            @JsonProperty("amount") BigDecimal amount) {
        
        this.price = price;
        this.amount = amount;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
}
