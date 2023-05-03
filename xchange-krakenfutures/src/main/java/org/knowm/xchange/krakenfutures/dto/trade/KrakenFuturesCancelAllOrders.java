package org.knowm.xchange.krakenfutures.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.krakenfutures.dto.KrakenFuturesResult;

import java.util.List;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KrakenFuturesCancelAllOrders extends KrakenFuturesResult {

    private final KrakenFuturesCancelStatus cancelStatus;

    public KrakenFuturesCancelAllOrders(
            @JsonProperty("result") String result,
            @JsonProperty("error") String error,
            @JsonProperty("cancelStatus") KrakenFuturesCancelStatus cancelStatus) {
        super(result, error);
        this.cancelStatus = cancelStatus;
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class KrakenFuturesCancelStatus{

        private final List<KrakenFuturesOrderId> orderIds;

        public KrakenFuturesCancelStatus(
                @JsonProperty("cancelledOrders") List<KrakenFuturesOrderId> orderIds) {
            this.orderIds = orderIds;
        }
    }

    @Getter
    public static class KrakenFuturesOrderId{

        private final String orderId;

        public KrakenFuturesOrderId(
                @JsonProperty("order_id") String orderId) {
            this.orderId = orderId;
        }
    }
}
