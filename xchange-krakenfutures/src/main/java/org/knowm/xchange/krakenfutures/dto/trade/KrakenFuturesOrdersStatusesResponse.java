package org.knowm.xchange.krakenfutures.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.krakenfutures.dto.KrakenFuturesResult;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KrakenFuturesOrdersStatusesResponse extends KrakenFuturesResult {

    private final List<KrakenFuturesOrder> orders;

    public KrakenFuturesOrdersStatusesResponse(
            @JsonProperty("result") String result,
            @JsonProperty("error") String error,
            @JsonProperty("orders") List<KrakenFuturesOrder> orders) {
        super(result, error);
        this.orders = orders;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter
    public static class KrakenFuturesOrder{
        private final KrakenFuturesOrderDetails order;

        public KrakenFuturesOrder(
                @JsonProperty("order") KrakenFuturesOrderDetails order) {
            this.order = order;
        }
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class KrakenFuturesOrderDetails{
        private final String cliOrdId;
        private final BigDecimal filled;
        private final Date lastUpdateTimestamp;
        private final BigDecimal limitPrice;
        private final String orderId;
        private final BigDecimal quantity;
        private final boolean isReduceOnly;
        private final KrakenFuturesOrderSide side;
        private final String symbol;
        private final KrakenFuturesOrderStatus status;

        public KrakenFuturesOrderDetails(
                @JsonProperty("cliOrdId") String cliOrdId,
                @JsonProperty("filled") BigDecimal filled,
                @JsonProperty("lastUpdateTimestamp") Date lastUpdateTimestamp,
                @JsonProperty("limitPrice") BigDecimal limitPrice,
                @JsonProperty("orderId") String orderId,
                @JsonProperty("quantity") BigDecimal quantity,
                @JsonProperty("reduceOnly") boolean isReduceOnly,
                @JsonProperty("side") KrakenFuturesOrderSide side,
                @JsonProperty("symbol") String symbol,
                @JsonProperty("status") KrakenFuturesOrderStatus status) {
            this.cliOrdId = cliOrdId;
            this.filled = filled;
            this.lastUpdateTimestamp = lastUpdateTimestamp;
            this.limitPrice = limitPrice;
            this.orderId = orderId;
            this.quantity = quantity;
            this.isReduceOnly = isReduceOnly;
            this.side = side;
            this.symbol = symbol;
            this.status = status;
        }
    }
}
