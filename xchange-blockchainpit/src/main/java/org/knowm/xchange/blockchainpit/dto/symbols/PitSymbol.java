package org.knowm.xchange.blockchainpit.dto.symbols;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

// {
//      "base_currency":"BTC",
//      "base_currency_scale":8,
//      "counter_currency":"PAX",
//      "counter_currency_scale":8,
//      "min_price_increment":10000000,
//      "min_price_increment_scale":8,
//      "min_order_size":50000,
//      "min_order_size_scale":8,
//      "max_order_size":0,
//      "max_order_size_scale":8,
//      "lot_size":1,
//      "lot_size_scale":8,
//      "status":"open",
//      "id":16,
//      "auction_price":0.0,
//      "auction_size":0.0,
//      "auction_time":"",
//      "imbalance":0.0
// }
public class PitSymbol {
    private final String base_currency;
    private final int base_currency_scale;
    private final String counter_currency;
    private final int counter_currency_scale;
    private final Long min_price_increment;
    private final int min_price_increment_scale;
    private final Long min_order_size;
    private final int min_order_size_scale;
    private final Long max_order_size;
    private final int max_order_size_scale;
    private final Long lot_size;
    private final int lot_size_scale;
    private final String status;
    private final int id;
    private final BigDecimal auction_price;
    private final BigDecimal auction_size;
    private final String auction_time;
    private final BigDecimal imbalance;

    public PitSymbol(
        @JsonProperty("base_currency") String base_currency,
        @JsonProperty("base_currency_scale") int base_currency_scale,
        @JsonProperty("counter_currency") String counter_currency,
        @JsonProperty("counter_currency_scale") int counter_currency_scale,
        @JsonProperty("min_price_increment") Long min_price_increment,
        @JsonProperty("min_price_increment_scale") int min_price_increment_scale,
        @JsonProperty("min_order_size") Long min_order_size,
        @JsonProperty("min_order_size_scale") int min_order_size_scale,
        @JsonProperty("max_order_size") Long max_order_size,
        @JsonProperty("max_order_size_scale") int max_order_size_scale,
        @JsonProperty("lot_size") Long lot_size,
        @JsonProperty("lot_size_scale") int lot_size_scale,
        @JsonProperty("status") String status,
        @JsonProperty("id") int id,
        @JsonProperty("auction_price") BigDecimal auction_price,
        @JsonProperty("auction_size") BigDecimal auction_size,
        @JsonProperty("auction_time") String auction_time,
        @JsonProperty("imbalance") BigDecimal imbalance
    ) {
        this.base_currency = base_currency;
        this.base_currency_scale = base_currency_scale;
        this.counter_currency = counter_currency;
        this.counter_currency_scale = counter_currency_scale;
        this.min_price_increment = min_price_increment;
        this.min_price_increment_scale = min_price_increment_scale;
        this.min_order_size = min_order_size;
        this.min_order_size_scale = min_order_size_scale;
        this.max_order_size = max_order_size;
        this.max_order_size_scale = max_order_size_scale;
        this.lot_size = lot_size;
        this.lot_size_scale = lot_size_scale;
        this.status = status;
        this.id = id;
        this.auction_price = auction_price;
        this.auction_size = auction_size;
        this.auction_time = auction_time;
        this.imbalance = imbalance;
    }

    public String getBaseCurrency() {
        return base_currency;
    }

    public int getCurrencyScale() {
        return base_currency_scale;
    }

    public String getCounterCurrency() {
        return counter_currency;
    }

    public int getCounterCurrencyScale() {
        return counter_currency_scale;
    }

    public Long getMinPriceIncrement() {
        return min_price_increment;
    }

    public int getMinPriceIncrementScale() {
        return min_price_increment_scale;
    }

    public Long getMinOrderSize() {
        return min_order_size;
    }

    public int getMinOrderSizeScale() {
        return min_order_size_scale;
    }

    public Long getMaxOrderSize() {
        return max_order_size;
    }

    public int getMaxOrderSizeScale() {
        return max_order_size_scale;
    }

    public Long getLotSize() {
        return lot_size;
    }

    public int getLotSizeScale() {
        return lot_size_scale;
    }

    public String getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getAuctionPrice() {
        return auction_price;
    }

    public BigDecimal getAuctionSize() {
        return auction_size;
    }

    public String getAuctionTime() {
        return auction_time;
    }

    public BigDecimal getImbalance() {
        return imbalance;
    }
}
