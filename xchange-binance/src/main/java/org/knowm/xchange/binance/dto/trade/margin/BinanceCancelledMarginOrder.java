package org.knowm.xchange.binance.dto.trade.margin;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.binance.dto.trade.BinanceCancelledOrder;

public class BinanceCancelledMarginOrder extends BinanceCancelledOrder {
    public final MarginAccountType marginAccountType;

    public BinanceCancelledMarginOrder(
            @JsonProperty("symbol") String symbol,
            @JsonProperty("isIsolated") MarginAccountType marginAccountType,
            @JsonProperty("origClientOrderId") String origClientOrderId,
            @JsonProperty("orderId") long orderId,
            @JsonProperty("clientOrderId") String clientOrderId,
            @JsonProperty("price") String price,
            @JsonProperty("origQty") String origQty,
            @JsonProperty("executedQty") String executedQty,
            @JsonProperty("cummulativeQuoteQty") String cummulativeQuoteQty,
            @JsonProperty("status") String status,
            @JsonProperty("timeInForce") String timeInForce,
            @JsonProperty("type") String type,
            @JsonProperty("side") String side) {
        super(symbol, origClientOrderId, orderId, clientOrderId, price, origQty, executedQty, cummulativeQuoteQty, status, timeInForce, type, side);

        this.marginAccountType = marginAccountType;
    }
}
