package org.knowm.xchange.cobinhood.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CobinhoodCurrencyPair {
    private final String id;
    private final BigDecimal last_price;
    private final BigDecimal lowest_ask;
    private final BigDecimal highest_bid;
    private final BigDecimal base_volume;
    private final BigDecimal quote_volume;
    private final boolean is_frozen;
    private final BigDecimal high_24hr;
    private final BigDecimal low_24hr;
    private final BigDecimal percent_changed_24hr;

    public CobinhoodCurrencyPair(@JsonProperty("id") String id,
                                 @JsonProperty("last_price") BigDecimal last_price,
                                 @JsonProperty("lowest_ask") BigDecimal lowest_ask,
                                 @JsonProperty("highest_bid") BigDecimal highest_bid,
                                 @JsonProperty("base_volume") BigDecimal base_volume,
                                 @JsonProperty("quote_volume") BigDecimal quote_volume,
                                 @JsonProperty("is_frozen") boolean is_frozen,
                                 @JsonProperty("high_24hr") BigDecimal high_24hr,
                                 @JsonProperty("low_24hr") BigDecimal low_24hr,
                                 @JsonProperty("percent_changed_24hr") BigDecimal percent_changed_24hr) {
        this.id = id;
        this.last_price = last_price;
        this.lowest_ask = lowest_ask;
        this.highest_bid = highest_bid;
        this.base_volume = base_volume;
        this.quote_volume = quote_volume;
        this.is_frozen = is_frozen;
        this.high_24hr = high_24hr;
        this.low_24hr = low_24hr;
        this.percent_changed_24hr = percent_changed_24hr;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getLast_price() {
        return last_price;
    }

    public BigDecimal getLowest_ask() {
        return lowest_ask;
    }

    public BigDecimal getHighest_bid() {
        return highest_bid;
    }

    public BigDecimal getBase_volume() {
        return base_volume;
    }

    public BigDecimal getQuote_volume() {
        return quote_volume;
    }

    public boolean isIs_frozen() {
        return is_frozen;
    }

    public BigDecimal getHigh_24hr() {
        return high_24hr;
    }

    public BigDecimal getLow_24hr() {
        return low_24hr;
    }

    public BigDecimal getPercent_changed_24hr() {
        return percent_changed_24hr;
    }

    @Override
    public String toString() {
        return "CobinhoodCurrencyPair{" +
                "id='" + id + '\'' +
                ", last_price=" + last_price +
                ", lowest_ask=" + lowest_ask +
                ", highest_bid=" + highest_bid +
                ", base_volume=" + base_volume +
                ", quote_volume=" + quote_volume +
                ", is_frozen=" + is_frozen +
                ", high_24hr=" + high_24hr +
                ", low_24hr=" + low_24hr +
                ", percent_changed_24hr=" + percent_changed_24hr +
                '}';
    }
}
