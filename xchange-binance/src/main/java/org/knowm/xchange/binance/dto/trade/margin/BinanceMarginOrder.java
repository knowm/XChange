package org.knowm.xchange.binance.dto.trade.margin;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.binance.dto.trade.*;

import java.math.BigDecimal;

public class BinanceMarginOrder extends BinanceOrder {
    public final MarginAccountType marginAccountType;

    public BinanceMarginOrder(
            @JsonProperty("symbol") String symbol,
            @JsonProperty("orderId") long orderId,
            @JsonProperty("clientOrderId") String clientOrderId,
            @JsonProperty("price") BigDecimal price,
            @JsonProperty("origQty") BigDecimal origQty,
            @JsonProperty("executedQty") BigDecimal executedQty,
            @JsonProperty("cummulativeQuoteQty") BigDecimal cummulativeQuoteQty,
            @JsonProperty("status") OrderStatus status,
            @JsonProperty("timeInForce") TimeInForce timeInForce,
            @JsonProperty("type") OrderType type,
            @JsonProperty("side") OrderSide side,
            @JsonProperty("stopPrice") BigDecimal stopPrice,
            @JsonProperty("icebergQty") BigDecimal icebergQty,
            @JsonProperty("isIsolated") MarginAccountType marginAccountType,
            @JsonProperty("time") long time) {
        super(symbol, orderId, clientOrderId, price, origQty, executedQty, cummulativeQuoteQty, status, timeInForce, type, side, stopPrice, icebergQty, time);

        this.marginAccountType = marginAccountType;
    }
}
