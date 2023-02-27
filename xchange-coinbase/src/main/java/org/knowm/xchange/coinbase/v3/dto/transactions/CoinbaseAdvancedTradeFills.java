package org.knowm.xchange.coinbase.v3.dto.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Getter
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CoinbaseAdvancedTradeFills {

    String entryId;
    String tradeId;
    String orderId;
    String tradeTime;
    String tradeType;
    BigDecimal price;
    BigDecimal size;
    BigDecimal commission;
    String productId;
    String sequenceTimestamp;
    String liquidityIndicator;
    String sizeInQuote;
    String userId;
    String side;

    public CoinbaseAdvancedTradeFills(
            @JsonProperty("entry_id") String entryId,
            @JsonProperty("trade_id") String tradeId,
            @JsonProperty("order_id") String orderId,
            @JsonProperty("trade_time") String tradeTime,
            @JsonProperty("trade_type") String tradeType,
            @JsonProperty("price") BigDecimal price,
            @JsonProperty("size") BigDecimal size,
            @JsonProperty("commission") BigDecimal commission,
            @JsonProperty("product_id") String productId,
            @JsonProperty("sequence_timestamp") String sequenceTimestamp,
            @JsonProperty("liquidity_indicator") String liquidityIndicator,
            @JsonProperty("size_in_quote") String sizeInQuote,
            @JsonProperty("user_id") String userId,
            @JsonProperty("side") String side) {

        this.entryId = entryId;
        this.tradeId = tradeId;
        this.orderId = orderId;
        this.tradeTime = tradeTime;
        this.tradeType = tradeType;
        this.price = price;
        this.size = size;
        this.commission = commission;
        this.productId = productId;
        this.sequenceTimestamp = sequenceTimestamp;
        this.liquidityIndicator = liquidityIndicator;
        this.sizeInQuote = sizeInQuote;
        this.userId = userId;
        this.side = side;
    }
}
