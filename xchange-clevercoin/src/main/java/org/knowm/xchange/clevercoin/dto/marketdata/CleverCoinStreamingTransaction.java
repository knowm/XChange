package org.knowm.xchange.clevercoin.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

//This is to deal with an inconsistency in CleverCoin's APIs, where JsonProperty for
//trade ID is "id" when polling and "tid" when streaming.
public class CleverCoinStreamingTransaction extends CleverCoinTransaction {
  public CleverCoinStreamingTransaction(@JsonProperty("date") long date, @JsonProperty("id") int tid, @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount) {
    super(date, tid, price, amount);
  }
}
