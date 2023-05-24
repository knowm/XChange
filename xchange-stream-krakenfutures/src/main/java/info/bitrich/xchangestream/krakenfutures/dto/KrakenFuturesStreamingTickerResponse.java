package info.bitrich.xchangestream.krakenfutures.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KrakenFuturesStreamingTickerResponse {

    private final Date time;
    private final String feed;
    private final String product_id;
    private final BigDecimal bid;
    private final BigDecimal ask;
    private final BigDecimal bid_size;
    private final BigDecimal ask_size;
    private final BigDecimal volume;
    private final BigDecimal last;
    private final BigDecimal change;
    private final BigDecimal funding_rate;
    private final BigDecimal relative_funding_rate;
    private final BigDecimal funding_rate_prediction;
    private final BigDecimal openInterest;
    private final Date nextFundingRateTime;
    private final BigDecimal volumeQuote;

    public KrakenFuturesStreamingTickerResponse(
            @JsonProperty("time") Date time,
            @JsonProperty("feed") String feed,
            @JsonProperty("product_id") String product_id,
            @JsonProperty("bid") BigDecimal bid,
            @JsonProperty("ask") BigDecimal ask,
            @JsonProperty("bid_size") BigDecimal bid_size,
            @JsonProperty("ask_size") BigDecimal ask_size,
            @JsonProperty("volume") BigDecimal volume,
            @JsonProperty("last") BigDecimal last,
            @JsonProperty("change") BigDecimal change,
            @JsonProperty("funding_rate") BigDecimal funding_rate,
            @JsonProperty("relative_funding_rate") BigDecimal relative_funding_rate,
            @JsonProperty("funding_rate_prediction") BigDecimal funding_rate_prediction,
            @JsonProperty("openInterest") BigDecimal openInterest,
            @JsonProperty("next_funding_rate_time") Date nextFundingRateTime,
            @JsonProperty("volumeQuote") BigDecimal volumeQuote) {
        this.time = time;
        this.feed = feed;
        this.product_id = product_id;
        this.bid = bid;
        this.ask = ask;
        this.bid_size = bid_size;
        this.ask_size = ask_size;
        this.volume = volume;
        this.last = last;
        this.change = change;
        this.funding_rate = funding_rate;
        this.relative_funding_rate = relative_funding_rate;
        this.funding_rate_prediction = funding_rate_prediction;
        this.openInterest = openInterest;
        this.nextFundingRateTime = nextFundingRateTime;
        this.volumeQuote = volumeQuote;
    }
}
