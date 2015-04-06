package com.xeiam.xchange.coinmate.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 *
 * @author Martin Stachon
 */
public class CoinmateTransactionsEntry {
    private final long timestamp;
    private final String transactionId;
    private final BigDecimal price;
    private final BigDecimal amount;
    private final String currencyPair;
    
    public CoinmateTransactionsEntry(@JsonProperty("timestamp") long timestamp,
            @JsonProperty("transactionId") String transactionId,
            @JsonProperty("price") BigDecimal price, 
            @JsonProperty("amount") BigDecimal amount, 
            @JsonProperty("currencyPair") String currencyPair) {
        
        this.timestamp = timestamp;
        this.transactionId = transactionId;
        this.price = price;
        this.amount = amount;
        this.currencyPair = currencyPair;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public String getTransactionId() {
        return transactionId;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public String getCurrencyPair() {
        return currencyPair;
    }

}
