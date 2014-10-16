package com.xeiam.xchange.cryptonit.v2.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created by Yar.kh on 03/10/14.
 */
public class NewOrderRequest {

    String bid_currency;
    String ask_currency;
    BigDecimal bid_amount;
    BigDecimal ask_amount;
    Long nonce;
    Long timestamp;

    public NewOrderRequest(@JsonProperty("bid_currency") String bid_currency,
                           @JsonProperty("ask_currency") String ask_currency,
                           @JsonProperty("bid_amount") BigDecimal bid_amount,
                           @JsonProperty("ask_amount") BigDecimal ask_amount,
                           @JsonProperty("nonce") Long nonce,
                           @JsonProperty("timestamp") Long timestamp) {
        this.bid_currency = bid_currency;
        this.ask_currency = ask_currency;
        this.bid_amount = bid_amount;
        this.ask_amount = ask_amount;
        this.nonce = nonce;
        this.timestamp = timestamp;
    }

    public String getBid_currency() {
        return bid_currency;
    }

    public String getAsk_currency() {
        return ask_currency;
    }

    public BigDecimal getBid_amount() {
        return bid_amount;
    }

    public BigDecimal getAsk_amount() {
        return ask_amount;
    }

    public Long getNonce() {
        return nonce;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
