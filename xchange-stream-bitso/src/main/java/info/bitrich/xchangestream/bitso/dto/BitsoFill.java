package info.bitrich.xchangestream.bitso.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class BitsoFill {
    private final String tradeId;
    private final String productId;
    private final BigDecimal price;
    private final BigDecimal size;
    private final String orderId;
    private final String createdAt;
    private final String liquidity;
    private final BigDecimal fee;
    private final boolean settled;
    private final String side;

    public BitsoFill(@JsonProperty("trade_id") String tradeId, @JsonProperty("product_id") String productId, @JsonProperty("price") BigDecimal price, @JsonProperty("size") BigDecimal size, @JsonProperty("order_id") String orderId, @JsonProperty("created_at") String createdAt, @JsonProperty("liquidity") String liquidity, @JsonProperty("fee") BigDecimal fee, @JsonProperty("settled") boolean settled, @JsonProperty("side") String side) {
        this.tradeId = tradeId;
        this.productId = productId;
        this.price = price;
        this.size = size;
        this.orderId = orderId;
        this.createdAt = createdAt;
        this.liquidity = liquidity;
        this.fee = fee;
        this.settled = settled;
        this.side = side;
    }

    public String getTradeId() {
        return this.tradeId;
    }

    public String getProductId() {
        return this.productId;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public BigDecimal getSize() {
        return this.size;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public String getLiquidity() {
        return this.liquidity;
    }

    public BigDecimal getFee() {
        return this.fee;
    }

    public boolean isSettled() {
        return this.settled;
    }

    public String getSide() {
        return this.side;
    }

    public String toString() {
        return "BitsoExFill{tradeId='" + this.tradeId + '\'' + ", productId='" + this.productId + '\'' + ", price=" + this.price + ", size=" + this.size + ", orderId='" + this.orderId + '\'' + ", createdAt='" + this.createdAt + '\'' + ", liquidity='" + this.liquidity + '\'' + ", fee=" + this.fee + ", settled=" + this.settled + ", side='" + this.side + '\'' + '}';
    }
}
