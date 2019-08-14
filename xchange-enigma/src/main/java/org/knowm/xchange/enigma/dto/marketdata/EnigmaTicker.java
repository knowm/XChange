package org.knowm.xchange.enigma.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EnigmaTicker {
    @JsonProperty("product_id")
    private int productId;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("last")
    private BigDecimal last;

    @JsonProperty("open")
    private BigDecimal open;

    @JsonProperty("bid")
    private BigDecimal bid;

    @JsonProperty("ask")
    private BigDecimal ask;

    @JsonProperty("bid_qty")
    private BigDecimal bidQty;

    @JsonProperty("ask_qty")
    private BigDecimal askQty;
}
