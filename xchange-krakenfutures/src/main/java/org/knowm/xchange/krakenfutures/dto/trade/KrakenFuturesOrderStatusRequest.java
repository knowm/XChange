package org.knowm.xchange.krakenfutures.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KrakenFuturesOrderStatusRequest {

    private final String[] orderIds;

    public KrakenFuturesOrderStatusRequest(
            @JsonProperty("orderIds") String[] orderIds) {
        this.orderIds = orderIds;
    }
}
