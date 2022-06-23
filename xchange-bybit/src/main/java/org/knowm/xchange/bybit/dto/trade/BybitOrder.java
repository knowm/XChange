package org.knowm.xchange.bybit.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BybitOrder {

    private final String accountId;
    private final String exchangeId;
    private final String symbol;
    private final String symbolName;
    private final String orderLinkId;
    private final String orderId;
    private final String price;
    private final String origQty;
    private final String executedQty;
    private final String cummulativeQuoteQty;
    private final String avgPrice;
    private final String status;
    private final String timeInForce;
    private final String type;
    private final String side;
    private final String stopPrice;
    private final String icebergQty;
    private final String time;
    private final String updateTime;
    private final boolean isWorking;
    private final String locked;

    @JsonCreator
    public BybitOrder(
            @JsonProperty("accountId") String accountId,
            @JsonProperty("exchangeId") String exchangeId,
            @JsonProperty("symbol") String symbol,
            @JsonProperty("symbolName") String symbolName,
            @JsonProperty("orderLinkId") String orderLinkId,
            @JsonProperty("orderId") String orderId,
            @JsonProperty("price") String price,
            @JsonProperty("origQty") String origQty,
            @JsonProperty("executedQty") String executedQty,
            @JsonProperty("cummulativeQuoteQty") String cummulativeQuoteQty,
            @JsonProperty("avgPrice") String avgPrice,
            @JsonProperty("status") String status,
            @JsonProperty("timeInForce") String timeInForce,
            @JsonProperty("type") String type,
            @JsonProperty("side") String side,
            @JsonProperty("stopPrice") String stopPrice,
            @JsonProperty("icebergQty") String icebergQty,
            @JsonProperty("time") String time,
            @JsonProperty("updateTime") String updateTime,
            @JsonProperty("isWorking") boolean isWorking,
            @JsonProperty("locked") String locked
    ) {
        this.accountId = accountId;
        this.exchangeId = exchangeId;
        this.symbol = symbol;
        this.symbolName = symbolName;
        this.orderLinkId = orderLinkId;
        this.orderId = orderId;
        this.price = price;
        this.origQty = origQty;
        this.executedQty = executedQty;
        this.cummulativeQuoteQty = cummulativeQuoteQty;
        this.avgPrice = avgPrice;
        this.status = status;
        this.timeInForce = timeInForce;
        this.type = type;
        this.side = side;
        this.stopPrice = stopPrice;
        this.icebergQty = icebergQty;
        this.time = time;
        this.updateTime = updateTime;
        this.isWorking = isWorking;
        this.locked = locked;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getExchangeId() {
        return exchangeId;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public String getOrderLinkId() {
        return orderLinkId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getPrice() {
        return price;
    }

    public String getOrigQty() {
        return origQty;
    }

    public String getExecutedQty() {
        return executedQty;
    }

    public String getCummulativeQuoteQty() {
        return cummulativeQuoteQty;
    }

    public String getAvgPrice() {
        return avgPrice;
    }

    public String getStatus() {
        return status;
    }

    public String getTimeInForce() {
        return timeInForce;
    }

    public String getType() {
        return type;
    }

    public String getSide() {
        return side;
    }

    public String getStopPrice() {
        return stopPrice;
    }

    public String getIcebergQty() {
        return icebergQty;
    }

    public String getTime() {
        return time;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public String getLocked() {
        return locked;
    }
}
