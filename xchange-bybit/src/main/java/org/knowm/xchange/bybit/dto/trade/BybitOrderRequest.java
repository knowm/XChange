package org.knowm.xchange.bybit.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BybitOrderRequest {

    private final String accountId;
    private final String symbol;
    private final String symbolName;
    private final String orderLinkId;
    private final String orderId;
    private final String transactTime;
    private final String price;
    private final String origQty;
    private final String executedQty;
    private final String status;
    private final String timeInForce;
    private final String type;
    private final String side;

    @JsonCreator
    public BybitOrderRequest(
            @JsonProperty("accountId") String accountId,
            @JsonProperty("symbol") String symbol,
            @JsonProperty("symbolName") String symbolName,
            @JsonProperty("orderLinkId") String orderLinkId,
            @JsonProperty("orderId") String orderId,
            @JsonProperty("transactTime") String transactTime,
            @JsonProperty("price") String price,
            @JsonProperty("origQty") String origQty,
            @JsonProperty("executedQty") String executedQty,
            @JsonProperty("status") String status,
            @JsonProperty("timeInForce") String timeInForce,
            @JsonProperty("type") String type,
            @JsonProperty("side") String side) {
        this.accountId = accountId;
        this.symbol = symbol;
        this.symbolName = symbolName;
        this.orderLinkId = orderLinkId;
        this.orderId = orderId;
        this.transactTime = transactTime;
        this.price = price;
        this.origQty = origQty;
        this.executedQty = executedQty;
        this.status = status;
        this.timeInForce = timeInForce;
        this.type = type;
        this.side = side;
    }

    public String getAccountId() {
        return accountId;
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

    public String getTransactTime() {
        return transactTime;
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

    @Override
    public String toString() {
        return "BybitOrderRequest{" +
                "accountId='" + accountId + '\'' +
                ", symbol='" + symbol + '\'' +
                ", symbolName='" + symbolName + '\'' +
                ", orderLinkId='" + orderLinkId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", transactTime='" + transactTime + '\'' +
                ", price='" + price + '\'' +
                ", origQty='" + origQty + '\'' +
                ", executedQty='" + executedQty + '\'' +
                ", status='" + status + '\'' +
                ", timeInForce='" + timeInForce + '\'' +
                ", type='" + type + '\'' +
                ", side='" + side + '\'' +
                '}';
    }
}
