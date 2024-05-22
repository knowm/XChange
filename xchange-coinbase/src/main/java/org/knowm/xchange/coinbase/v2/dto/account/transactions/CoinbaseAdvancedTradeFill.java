package org.knowm.xchange.coinbase.v2.dto.account.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CoinbaseAdvancedTradeFill {

    private final String fillPrice;
    private final String productId;
    private final String orderId;
    private final String commission;
    private final String orderSide;

    public CoinbaseAdvancedTradeFill(
            @JsonProperty("fill_price") String fillPrice,
            @JsonProperty("product_id") String productId,
            @JsonProperty("order_id") String orderId,
            @JsonProperty("commission") String commission,
            @JsonProperty("order_side") String orderSide) {
        this.fillPrice = fillPrice;
        this.productId = productId;
        this.orderId = orderId;
        this.commission = commission;
        this.orderSide = orderSide;
    }

}
