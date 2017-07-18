package org.knowm.xchange.luno.dto.trade;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LunoUserTrades {

    private final UserTrade[] trades;
    
    public LunoUserTrades(@JsonProperty(value="trades", required=true) UserTrade[] trades) {
        this.trades = trades != null ? trades : new UserTrade[0];
    }

    public UserTrade[] getTrades() {
        UserTrade[] copy = new UserTrade[trades.length];
        System.arraycopy(trades, 0, copy, 0, trades.length);
        return copy;
    }
    
    @Override
    public String toString() {
        return "LunoUserTrades [trades(" + trades.length + ")=" + Arrays.toString(trades) + "]";
    }

    public static class UserTrade {
        public final String pair;
        public final String orderId;
        public final OrderType type;
        public final long timestamp;
        public final BigDecimal price;
        public final BigDecimal volume;
        public final BigDecimal base;
        public final BigDecimal counter;
        public final BigDecimal feeBase;
        public final BigDecimal feeCounter;
        public final boolean buy;

        public UserTrade(
                @JsonProperty(value="pair", required=false) String pair
                , @JsonProperty(value="order_id", required=true) String orderId
                , @JsonProperty(value="type", required=false) OrderType type
                , @JsonProperty(value="timestamp", required=true) long timestamp
                , @JsonProperty(value="price", required=true) BigDecimal price
                , @JsonProperty(value="volume", required=true) BigDecimal volume
                , @JsonProperty(value="base", required=true) BigDecimal base
                , @JsonProperty(value="counter", required=true) BigDecimal counter
                , @JsonProperty(value="fee_base", required=true) BigDecimal feeBase
                , @JsonProperty(value="fee_counter", required=true) BigDecimal feeCounter
                , @JsonProperty(value="is_buy", required=true) boolean buy
                ) {
            this.pair = pair;
            this.orderId = orderId;
            this.type = type;
            this.timestamp = timestamp;
            this.price = price;
            this.volume = volume;
            this.base = base;
            this.counter = counter;
            this.feeBase = feeBase;
            this.feeCounter = feeCounter;
            this.buy = buy;
        }
        
        public Date getTimestamp() {
            return new Date(timestamp);
        }

        @Override
        public String toString() {
            return "UserTrade [base=" + base + ", counter=" + counter + ", feeBase=" + feeBase + ", feeCounter=" + feeCounter
                    + ", buy=" + buy + ", orderId=" + orderId + ", pair=" + pair + ", price=" + price + ", timestamp=" + getTimestamp()
                    + ", type=" + type + ", volume=" + volume + "]";
        }

        
    }
}
