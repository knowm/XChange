package com.xeiam.xchange.bitbay.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created by Yar.kh on 10/10/14.
 */
public class BitbayBalance {

    // {"available":0,"locked":"0.00"}

    private BigDecimal available;
    private BigDecimal locked;

    public BitbayBalance(@JsonProperty("available") BigDecimal available, @JsonProperty("locked") BigDecimal locked) {
        this.available = available;
        this.locked = locked;
    }

    public BigDecimal getAvailable() {
        return available;
    }

    public BigDecimal getLocked() {
        return locked;
    }

    @Override
    public String toString() {
        return "BitbayBalance{" +
                "available=" + available +
                ", locked=" + locked +
                '}';
    }
}

