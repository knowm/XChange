package info.bitrich.xchangestream.bitso.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class  BitsoTrades {
    private final String timestamp;
    private final long tradeId;
    private final BigDecimal price;
    private final BigDecimal size;
    private final String side;
    private final String maker_order_id;
    private final String taker_order_id;

    public BitsoTrades(String timestamp, long tradeId, BigDecimal price, BigDecimal size, String side, String maker_order_id, String taker_order_id) {
        this.timestamp = timestamp;
        this.tradeId = tradeId;
        this.price = price;
        this.size = size;
        this.side = side;
        this.maker_order_id = maker_order_id;
        this.taker_order_id = taker_order_id;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public long getTradeId() {
        return this.tradeId;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public BigDecimal getSize() {
        return this.size;
    }

    public String getSide() {
        return this.side;
    }

    public String getMakerOrderId() {
        return this.maker_order_id;
    }

    public String getTakerOrderId() {
        return this.taker_order_id;
    }

    public String toString() {
        return "BitsoTrade [timestamp=" + this.timestamp + ", tradeId=" + this.tradeId + ", price=" + this.price + ", size=" + this.size + ", side=" + this.side + "]";
    }
}
