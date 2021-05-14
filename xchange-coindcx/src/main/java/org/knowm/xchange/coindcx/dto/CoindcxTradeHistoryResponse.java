package org.knowm.xchange.coindcx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CoindcxTradeHistoryResponse {

    private long id;
    private String orderId;
    private String side;
    private String feeAmount;
    private String ecode;
    private BigDecimal quantity;
    private BigDecimal price;
    private String symbol;
    private Long timestamp;

    public CoindcxTradeHistoryResponse(@JsonProperty("id") long id,
                                       @JsonProperty("order_id") String orderId,
                                       @JsonProperty("side") String side,
                                       @JsonProperty("fee_amount") String feeAmount,
                                       @JsonProperty("ecode") String ecode,
                                       @JsonProperty("quantity") BigDecimal quantity,
                                       @JsonProperty("price") BigDecimal price,
                                       @JsonProperty("symbol") String symbol,
                                       @JsonProperty("timestamp") Long timestamp) {
        this.id = id;
        this.orderId = orderId;
        this.side = side;
        this.feeAmount = feeAmount;
        this.ecode = ecode;
        this.quantity = quantity;
        this.price = price;
        this.symbol = symbol;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "CoindcxTradeHistoryResponse{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", side='" + side + '\'' +
                ", feeAmount='" + feeAmount + '\'' +
                ", ecode='" + ecode + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", symbol='" + symbol + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
