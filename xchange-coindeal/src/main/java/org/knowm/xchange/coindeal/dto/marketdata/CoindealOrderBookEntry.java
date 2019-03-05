package org.knowm.xchange.coindeal.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;

public class CoindealOrderBookEntry {

    @JsonProperty("price")
    private final BigDecimal price;
    @JsonProperty("amount")
    private final BigDecimal amount;

    public CoindealOrderBookEntry(
            @JsonProperty("price") BigDecimal price,
            @JsonProperty("amount") BigDecimal amount){
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
