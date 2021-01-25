package org.knowm.xchange.bitmax.dto.trade;

public class BitmaxCancelOrderRequestPayload {

    private final String orderId;

    private final String symbol;

    private final Long time;

    public BitmaxCancelOrderRequestPayload(String orderId, String symbol, Long time) {
        this.orderId = orderId;
        this.symbol = symbol;
        this.time = time;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getSymbol() {
        return symbol;
    }

    public Long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "BitmaxCancelOrderRequestPayload{" +
                ", orderId='" + orderId + '\'' +
                ", symbol='" + symbol + '\'' +
                ", time=" + time +
                '}';
    }
}
