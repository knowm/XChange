package info.bitrich.xchangestream.lgo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

public class LgoUserUpdateData {

    private final String type;
    private final String orderId;
    private final Date time;
    private final String orderType;
    private final BigDecimal price;
    private final String side;
    private final BigDecimal quantity;
    private final String reason;
    private final BigDecimal canceled;
    private final String tradeId;
    private final BigDecimal filledQuantity;
    private final BigDecimal remainingQuantity;
    private final BigDecimal fees;
    private final String liquidity;
    private final Date orderCreationTime;

    public LgoUserUpdateData(
            @JsonProperty("type") String type,
            @JsonProperty("order_id") String orderId,
            @JsonProperty("time") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") Date time,
            @JsonProperty("order_type") String orderType,
            @JsonProperty("price") BigDecimal price,
            @JsonProperty("side") String side,
            @JsonProperty("quantity") BigDecimal quantity,
            @JsonProperty("reason") String reason,
            @JsonProperty("canceled") BigDecimal canceled,
            @JsonProperty("trade_id") String tradeId,
            @JsonProperty("filled_quantity") BigDecimal filledQuantity,
            @JsonProperty("remaining_quantity") BigDecimal remainingQuantity,
            @JsonProperty("fees") BigDecimal fees,
            @JsonProperty("liquidity") String liquidity,
            @JsonProperty("order_creation_time") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") Date orderCreationTime) {
        this.type = type;
        this.orderId = orderId;
        this.time = time;
        this.orderType = orderType;
        this.price = price;
        this.side = side;
        this.quantity = quantity;
        this.reason = reason;
        this.canceled = canceled;
        this.tradeId = tradeId;
        this.filledQuantity = filledQuantity;
        this.remainingQuantity = remainingQuantity;
        this.fees = fees;
        this.liquidity = liquidity;
        this.orderCreationTime = orderCreationTime;
    }

    public String getType() {
        return type;
    }

    public String getOrderId() {
        return orderId;
    }

    public Date getTime() {
        return time;
    }

    public String getOrderType() {
        return orderType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getSide() {
        return side;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public String getReason() {
        return reason;
    }

    public BigDecimal getCanceled() {
        return canceled;
    }

    public String getTradeId() {
        return tradeId;
    }

    public BigDecimal getFilledQuantity() {
        return filledQuantity;
    }

    public BigDecimal getRemainingQuantity() {
        return remainingQuantity;
    }

    public BigDecimal getFees() {
        return fees;
    }

    public String getLiquidity() {
        return liquidity;
    }

    public Date getOrderCreationTime() {
        return orderCreationTime;
    }
}
