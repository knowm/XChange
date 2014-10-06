package com.xeiam.xchange.bitkonan.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * @author Piotr Ładyżyński
 */
public class BitKonanOrderBookElement {

    private BigDecimal price;
    private BigDecimal volume;

    public BitKonanOrderBookElement(@JsonProperty("usd") BigDecimal usd, @JsonProperty("btc") BigDecimal btc) {
        this.price=usd;
        this.volume=btc;
    }


    public BigDecimal getPrice() {
        return price;
    }


    public BigDecimal getVolume() {
        return volume;
    }

}
