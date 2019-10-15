package org.knowm.xchange.blockchainpit.dto.orderbook;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * {
 *   "id": "2353",
 *   "px": 8904.45,
 *   "qty": 5.0
 * }
 */
public class PitDepthItemL3 {
    private final String id;
    private final BigDecimal px;
    private final BigDecimal qty;

    public PitDepthItemL3(
        @JsonProperty("id") String id,
        @JsonProperty("px") BigDecimal px,
        @JsonProperty("qty") BigDecimal qty
    ) {
        this.id = id;
        this.px = px;
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return px;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public String getId() {
        return id;
    }
}