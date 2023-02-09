package dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/** Author: Vladimir Karol Created: 09-02-2023 */
public class GateioTickerResponseResult {

    private final String currencyPair;
    private final BigDecimal highestBid;
    private final BigDecimal changePercentage;
    private final BigDecimal low24hr;
    private final BigDecimal last;
    private final BigDecimal high24hr;
    private final BigDecimal lowestAsk;
    private final BigDecimal quoteVolume;
    private final BigDecimal baseVolume;

    public GateioTickerResponseResult(
            @JsonProperty("currency_pair") String currencyPair,
            @JsonProperty("last") BigDecimal last,
            @JsonProperty("lowestAsk") BigDecimal lowestAsk,
            @JsonProperty("highestBid") BigDecimal highestBid,
            @JsonProperty("change_percentage") BigDecimal changePercentage,
            @JsonProperty("baseVolume") BigDecimal baseVolume,
            @JsonProperty("quoteVolume") BigDecimal quoteVolume,
            @JsonProperty("high24hr") BigDecimal high24hr,
            @JsonProperty("low24hr") BigDecimal low24hr
    ) {
        this.currencyPair = currencyPair;
        this.last = last;
        this.lowestAsk = lowestAsk;
        this.highestBid = highestBid;
        this.changePercentage = changePercentage;
        this.baseVolume = baseVolume;
        this.quoteVolume = quoteVolume;
        this.high24hr = high24hr;
        this.low24hr = low24hr;
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public BigDecimal getHighestBid() {
        return highestBid;
    }

    public BigDecimal getChangePercentage() {
        return changePercentage;
    }

    public BigDecimal getLow24hr() {
        return low24hr;
    }

    public BigDecimal getLast() {
        return last;
    }

    public BigDecimal getHigh24hr() {
        return high24hr;
    }

    public BigDecimal getLowestAsk() {
        return lowestAsk;
    }

    public BigDecimal getQuoteVolume() {
        return quoteVolume;
    }

    public BigDecimal getBaseVolume() {
        return baseVolume;
    }

    @Override
    public String toString() {
        return "GateioTickerResponseResult{" +
                "currencyPair='" + currencyPair + '\'' +
                ", highestBid=" + highestBid +
                ", changePercentage=" + changePercentage +
                ", low24hr=" + low24hr +
                ", last=" + last +
                ", high24hr=" + high24hr +
                ", lowestAsk=" + lowestAsk +
                ", quoteVolume=" + quoteVolume +
                ", baseVolume=" + baseVolume +
                '}';
    }
}
