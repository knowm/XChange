package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitOrderBook {

  @JsonProperty("timestamp")
  public long timestamp;

  @JsonProperty("stats")
  public DeribitStats stats;

  @JsonProperty("state")
  public String state;

  @JsonProperty("settlement_price")
  public BigDecimal settlementPrice;

  @JsonProperty("open_interest")
  public BigDecimal openInterest;

  @JsonProperty("min_price")
  public BigDecimal minPrice;

  @JsonProperty("max_price")
  public BigDecimal maxPrice;

  @JsonProperty("mark_price")
  public BigDecimal markPrice;

  @JsonProperty("last_price")
  public BigDecimal lastPrice;

  @JsonProperty("instrument_name")
  public String instrumentName;

  @JsonProperty("index_price")
  public BigDecimal indexPrice;

  @JsonProperty("funding_8h")
  public BigDecimal funding8h;

  @JsonProperty("greeks")
  public DeribitGreeks greeks;

  @JsonProperty("current_funding")
  public BigDecimal currentFunding;

  @JsonProperty("change_id")
  public long changeId;

  @JsonProperty("bids")
  public List<List<BigDecimal>> bids = null;

  @JsonProperty("best_bid_price")
  public BigDecimal bestBidPrice;

  @JsonProperty("best_bid_amount")
  public BigDecimal bestBidAmount;

  @JsonProperty("best_ask_price")
  public BigDecimal bestAskPrice;

  @JsonProperty("best_ask_amount")
  public BigDecimal bestAskAmount;

  @JsonProperty("asks")
  public List<List<BigDecimal>> asks;

  public long getTimestamp() {
    return timestamp;
  }

  public DeribitStats getStats() {
    return stats;
  }

  public String getState() {
    return state;
  }

  public BigDecimal getSettlementPrice() {
    return settlementPrice;
  }

  public BigDecimal getOpenInterest() {
    return openInterest;
  }

  public BigDecimal getMinPrice() {
    return minPrice;
  }

  public BigDecimal getMaxPrice() {
    return maxPrice;
  }

  public BigDecimal getMarkPrice() {
    return markPrice;
  }

  public BigDecimal getLastPrice() {
    return lastPrice;
  }

  public String getInstrumentName() {
    return instrumentName;
  }

  public BigDecimal getIndexPrice() {
    return indexPrice;
  }

  public BigDecimal getFunding8h() {
    return funding8h;
  }

  public DeribitGreeks getGreeks() {
    return greeks;
  }

  public BigDecimal getCurrentFunding() {
    return currentFunding;
  }

  public long getChangeId() {
    return changeId;
  }

  public List<List<BigDecimal>> getBids() {
    return bids;
  }

  public BigDecimal getBestBidPrice() {
    return bestBidPrice;
  }

  public BigDecimal getBestBidAmount() {
    return bestBidAmount;
  }

  public BigDecimal getBestAskPrice() {
    return bestAskPrice;
  }

  public BigDecimal getBestAskAmount() {
    return bestAskAmount;
  }

  public List<List<BigDecimal>> getAsks() {
    return asks;
  }
}
