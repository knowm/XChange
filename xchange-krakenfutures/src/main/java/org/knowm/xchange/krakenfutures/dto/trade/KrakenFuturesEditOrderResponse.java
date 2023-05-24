package org.knowm.xchange.krakenfutures.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.krakenfutures.dto.KrakenFuturesResult;

@Getter
@ToString
public class KrakenFuturesEditOrderResponse extends KrakenFuturesResult {

    private final KrakenFuturesEditStatus editStatus;

    public KrakenFuturesEditOrderResponse(
            @JsonProperty("result") String result,
            @JsonProperty("error") String error,
            @JsonProperty("editStatus") KrakenFuturesEditStatus editStatus) {
        super(result, error);
        this.editStatus = editStatus;
    }

    @Getter
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class KrakenFuturesEditStatus{
        private final String orderId;

        public KrakenFuturesEditStatus(
                @JsonProperty("orderId") String orderId) {
            this.orderId = orderId;
        }
    }
}
