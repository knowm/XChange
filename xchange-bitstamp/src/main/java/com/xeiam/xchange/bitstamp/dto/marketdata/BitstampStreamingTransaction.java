package com.xeiam.xchange.bitstamp.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

//This is to deal with an inconsistency in Bitstamp's APIs, where JsonProperty for
//trade ID is "id" when polling and "tid" when streaming.
public class BitstampStreamingTransaction extends BitstampTransaction {
    public BitstampStreamingTransaction(@JsonProperty("date") long date, @JsonProperty("id") int tid, @JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount) {
        super(date, tid, price, amount);
    }
}
