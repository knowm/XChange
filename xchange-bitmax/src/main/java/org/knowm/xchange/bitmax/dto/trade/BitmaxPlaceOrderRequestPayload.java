package org.knowm.xchange.bitmax.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

public class BitmaxPlaceOrderRequestPayload {

    private final String symbol;

    private final Long time;

    private final BigDecimal orderQty;

    private final BitmaxOrderType orderType;

    private final BitmaxSide side;

    @JsonIgnore
    private final String id;

    private final BigDecimal orderPrice;

    @JsonIgnore
    private final BigDecimal stopPrice;

    private final boolean postOnly;

    //GTC or OIC, default GTC
    @JsonIgnore
    private final String timeInForce;

    private final String respInst;

    public BitmaxPlaceOrderRequestPayload(String symbol, Long time, BigDecimal orderQty, BitmaxOrderType orderType, BitmaxSide side, String id, BigDecimal orderPrice, BigDecimal stopPrice, boolean postOnly, String timeInForce, String respInst) {
        this.symbol = symbol;
        this.time = time;
        this.orderQty = orderQty;
        this.orderType = orderType;
        this.side = side;
        this.id = id;
        this.orderPrice = orderPrice;
        this.stopPrice = stopPrice;
        this.postOnly = postOnly;
        this.timeInForce = timeInForce;
        this.respInst = respInst;
    }

    public String getSymbol() {
        return symbol;
    }

    public Long getTime() {
        return time;
    }

    public BigDecimal getOrderQty() {
        return orderQty;
    }

    public BitmaxOrderType getOrderType() {
        return orderType;
    }

    public BitmaxSide getSide() {
        return side;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public BigDecimal getStopPrice() {
        return stopPrice;
    }

    public boolean isPostOnly() {
        return postOnly;
    }

    public String getTimeInForce() {
        return timeInForce;
    }

    public String getRespInst() {
        return respInst;
    }

    @Override
    public String toString() {
        return "BitmaxPlaceOrderRequestPayload{" +
                "symbol='" + symbol + '\'' +
                ", time=" + time +
                ", orderQty=" + orderQty +
                ", orderType=" + orderType +
                ", side=" + side +
                ", id='" + id + '\'' +
                ", orderPrice=" + orderPrice +
                ", stopPrice=" + stopPrice +
                ", postOnly=" + postOnly +
                ", timeInForce='" + timeInForce + '\'' +
                ", respInst='" + respInst + '\'' +
                '}';
    }

    public enum BitmaxOrderType{
        market,
        limit,
        stop_market,
        stop_limit;
    }

    public enum BitmaxSide{
        buy,
        sell;
    }
}
