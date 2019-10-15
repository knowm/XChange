package org.knowm.xchange.blockchainpit.dto.orderbook;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;


/**
 * {
 *   "px": 8124.45,
 *   "qty": 123.45,
 *   "num": 1
 * }
 */
public class PitDepthItemL2 {
    private final BigDecimal px;
    private final BigDecimal qty;
    private final Long num;

    public PitDepthItemL2(
        @JsonProperty("px") BigDecimal px,
        @JsonProperty("qty") BigDecimal qty,
        @JsonProperty("num") Long num) {

        this.px = px;
        this.qty = qty;
        this.num = num;
    }

    public BigDecimal getPrice() {
        return px;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public Long getNum() {
        return num;
    }
}