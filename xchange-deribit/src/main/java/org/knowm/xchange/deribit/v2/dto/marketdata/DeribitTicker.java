package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitTicker {

    @JsonProperty("ask_iv") public BigDecimal askIv;
    @JsonProperty("best_ask_amount") public int bestAskAmount;
    @JsonProperty("best_ask_price") public BigDecimal bestAskPrice;
    @JsonProperty("best_bid_amount") public int bestBidAmount;
    @JsonProperty("best_bid_price") public BigDecimal bestBidPrice;
    @JsonProperty("bid_iv") public BigDecimal bidIv;
    @JsonProperty("greeks") public DeribitGreeks greeks;
    @JsonProperty("current_funding") public int currentFunding;
    @JsonProperty("funding_8h") public BigDecimal funding8h;
    @JsonProperty("index_price") public BigDecimal indexPrice;
    @JsonProperty("instrument_name") public String instrumentName;
    @JsonProperty("interest_rate") public int interestRate;
    @JsonProperty("last_price") public BigDecimal lastPrice;
    @JsonProperty("mark_iv") public BigDecimal markIv;
    @JsonProperty("mark_price") public BigDecimal markPrice;
    @JsonProperty("max_price") public BigDecimal maxPrice;
    @JsonProperty("min_price") public BigDecimal minPrice;
    @JsonProperty("open_interest") public BigDecimal openInterest;
    @JsonProperty("settlement_price") public BigDecimal settlementPrice;
    @JsonProperty("state") public String state;
    @JsonProperty("stats") public DeribitStats stats;
    @JsonProperty("timestamp") public long timestamp;
    @JsonProperty("underlying_index") public String underlyingIndex;
    @JsonProperty("underlying_price") public BigDecimal underlyingPrice;


    public BigDecimal getAskIv() {
        return askIv;
    }

    public int getBestAskAmount() {
        return bestAskAmount;
    }

    public BigDecimal getBestAskPrice() {
        return bestAskPrice;
    }

    public int getBestBidAmount() {
        return bestBidAmount;
    }

    public BigDecimal getBestBidPrice() {
        return bestBidPrice;
    }

    public BigDecimal getBidIv() {
        return bidIv;
    }

    public DeribitGreeks getGreeks() {
        return greeks;
    }

    public int getCurrentFunding() {
        return currentFunding;
    }

    public BigDecimal getFunding8h() {
        return funding8h;
    }

    public BigDecimal getIndexPrice() {
        return indexPrice;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public int getInterestRate() {
        return interestRate;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public BigDecimal getMarkIv() {
        return markIv;
    }

    public BigDecimal getMarkPrice() {
        return markPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public BigDecimal getOpenInterest() {
        return openInterest;
    }

    public BigDecimal getSettlementPrice() {
        return settlementPrice;
    }

    public String getState() {
        return state;
    }

    public DeribitStats getStats() {
        return stats;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getUnderlyingIndex() {
        return underlyingIndex;
    }

    public BigDecimal getUnderlyingPrice() {
        return underlyingPrice;
    }
}
