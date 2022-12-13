package org.knowm.xchange.binance.dto.trade.futures;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BinanceFutureNewOrder {

    private final String orderId;

    public BinanceFutureNewOrder(
            @JsonProperty("orderId") String orderId) {
        this.orderId = orderId;
    }
}
