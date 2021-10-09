package org.knowm.xchange.binance.futures.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.binance.dto.trade.OrderSide;
import org.knowm.xchange.binance.dto.trade.OrderStatus;
import org.knowm.xchange.binance.dto.trade.OrderType;
import org.knowm.xchange.binance.dto.trade.TimeInForce;

import java.math.BigDecimal;
import java.util.Date;

public class BinanceFuturesOrder {
    public final BigDecimal avgPrice;
    public final String clientOrderId;
    public final BigDecimal cumQuote;
    public final BigDecimal executedQty;
    public final long orderId;
    public final BigDecimal origQty;
    public final OrderType origType;
    public final BigDecimal price;
    public final boolean reduceOnly;
    public final OrderSide side;
    public final PositionSide positionSide;
    public final OrderStatus status;
    public final BigDecimal stopPrice;
    public final boolean closePosition;
    public final String symbol;
    public final long time;
    public final TimeInForce timeInForce;
    public final OrderType type;
    public final BigDecimal activatePrice;
    public final BigDecimal priceRate;
    public final long updateTime;
    public final WorkingType workingType;
    public final boolean priceProtect;

    public BinanceFuturesOrder(
            @JsonProperty("avgPrice") BigDecimal avgPrice,
            @JsonProperty("clientOrderId") String clientOrderId,
            @JsonProperty("cumQuote") BigDecimal cumQuote,
            @JsonProperty("executedQty") BigDecimal executedQty,
            @JsonProperty("orderId") long orderId,
            @JsonProperty("origQty") BigDecimal origQty,
            @JsonProperty("origType") OrderType origType,
            @JsonProperty("price") BigDecimal price,
            @JsonProperty("reduceOnly") boolean reduceOnly,
            @JsonProperty("side") OrderSide side,
            @JsonProperty("positionSide") PositionSide positionSide,
            @JsonProperty("status") OrderStatus status,
            @JsonProperty("stopPrice") BigDecimal stopPrice,
            @JsonProperty("closePosition") boolean closePosition,
            @JsonProperty("symbol") String symbol,
            @JsonProperty("time") long time,
            @JsonProperty("timeInForce") TimeInForce timeInForce,
            @JsonProperty("type") OrderType type,
            @JsonProperty("activatePrice") BigDecimal activatePrice,
            @JsonProperty("priceRate") BigDecimal priceRate,
            @JsonProperty("updateTime") long updateTime,
            @JsonProperty("workingType") WorkingType workingType,
            @JsonProperty("priceProtect") boolean priceProtect) {
        this.avgPrice = avgPrice;
        this.clientOrderId = clientOrderId;
        this.cumQuote = cumQuote;
        this.executedQty = executedQty;
        this.orderId = orderId;
        this.origQty = origQty;
        this.origType = origType;
        this.price = price;
        this.reduceOnly = reduceOnly;
        this.side = side;
        this.positionSide = positionSide;
        this.status = status;
        this.stopPrice = stopPrice;
        this.closePosition = closePosition;
        this.symbol = symbol;
        this.time = time;
        this.timeInForce = timeInForce;
        this.type = type;
        this.activatePrice = activatePrice;
        this.priceRate = priceRate;
        this.updateTime = updateTime;
        this.workingType = workingType;
        this.priceProtect = priceProtect;
    }

    public Date getTime() {
        return new Date(time);
    }
}
