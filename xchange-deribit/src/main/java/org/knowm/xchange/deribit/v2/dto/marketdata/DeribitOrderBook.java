package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitOrderBook {

  @JsonProperty("timestamp")
  private long timestamp;

  @JsonProperty("stats")
  private DeribitStats stats;

  @JsonProperty("state")
  private String state;

  @JsonProperty("settlement_price")
  private BigDecimal settlementPrice;

  @JsonProperty("open_interest")
  private BigDecimal openInterest;

  @JsonProperty("min_price")
  private BigDecimal minPrice;

  @JsonProperty("max_price")
  private BigDecimal maxPrice;

  @JsonProperty("mark_price")
  private BigDecimal markPrice;

  @JsonProperty("last_price")
  private BigDecimal lastPrice;

  @JsonProperty("instrument_name")
  private String instrumentName;

  @JsonProperty("index_price")
  private BigDecimal indexPrice;

  @JsonProperty("funding_8h")
  private BigDecimal funding8h;

  @JsonProperty("greeks")
  private DeribitGreeks greeks;

  @JsonProperty("current_funding")
  private BigDecimal currentFunding;

  @JsonProperty("change_id")
  private long changeId;

  @JsonProperty("bids")
  private List<List<BigDecimal>> bids = null;

  @JsonProperty("best_bid_price")
  private BigDecimal bestBidPrice;

  @JsonProperty("best_bid_amount")
  private BigDecimal bestBidAmount;

  @JsonProperty("best_ask_price")
  private BigDecimal bestAskPrice;

  @JsonProperty("best_ask_amount")
  private BigDecimal bestAskAmount;

  @JsonProperty("asks")
  private List<List<BigDecimal>> asks;

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public DeribitStats getStats() {
    return stats;
  }

  public void setStats(DeribitStats stats) {
    this.stats = stats;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public BigDecimal getSettlementPrice() {
    return settlementPrice;
  }

  public void setSettlementPrice(BigDecimal settlementPrice) {
    this.settlementPrice = settlementPrice;
  }

  public BigDecimal getOpenInterest() {
    return openInterest;
  }

  public void setOpenInterest(BigDecimal openInterest) {
    this.openInterest = openInterest;
  }

  public BigDecimal getMinPrice() {
    return minPrice;
  }

  public void setMinPrice(BigDecimal minPrice) {
    this.minPrice = minPrice;
  }

  public BigDecimal getMaxPrice() {
    return maxPrice;
  }

  public void setMaxPrice(BigDecimal maxPrice) {
    this.maxPrice = maxPrice;
  }

  public BigDecimal getMarkPrice() {
    return markPrice;
  }

  public void setMarkPrice(BigDecimal markPrice) {
    this.markPrice = markPrice;
  }

  public BigDecimal getLastPrice() {
    return lastPrice;
  }

  public void setLastPrice(BigDecimal lastPrice) {
    this.lastPrice = lastPrice;
  }

  public String getInstrumentName() {
    return instrumentName;
  }

  public void setInstrumentName(String instrumentName) {
    this.instrumentName = instrumentName;
  }

  public BigDecimal getIndexPrice() {
    return indexPrice;
  }

  public void setIndexPrice(BigDecimal indexPrice) {
    this.indexPrice = indexPrice;
  }

  public BigDecimal getFunding8h() {
    return funding8h;
  }

  public void setFunding8h(BigDecimal funding8h) {
    this.funding8h = funding8h;
  }

  public DeribitGreeks getGreeks() {
    return greeks;
  }

  public void setGreeks(DeribitGreeks greeks) {
    this.greeks = greeks;
  }

  public BigDecimal getCurrentFunding() {
    return currentFunding;
  }

  public void setCurrentFunding(BigDecimal currentFunding) {
    this.currentFunding = currentFunding;
  }

  public long getChangeId() {
    return changeId;
  }

  public void setChangeId(long changeId) {
    this.changeId = changeId;
  }

  public List<List<BigDecimal>> getBids() {
    return bids;
  }

  public void setBids(List<List<BigDecimal>> bids) {
    this.bids = bids;
  }

  public BigDecimal getBestBidPrice() {
    return bestBidPrice;
  }

  public void setBestBidPrice(BigDecimal bestBidPrice) {
    this.bestBidPrice = bestBidPrice;
  }

  public BigDecimal getBestBidAmount() {
    return bestBidAmount;
  }

  public void setBestBidAmount(BigDecimal bestBidAmount) {
    this.bestBidAmount = bestBidAmount;
  }

  public BigDecimal getBestAskPrice() {
    return bestAskPrice;
  }

  public void setBestAskPrice(BigDecimal bestAskPrice) {
    this.bestAskPrice = bestAskPrice;
  }

  public BigDecimal getBestAskAmount() {
    return bestAskAmount;
  }

  public void setBestAskAmount(BigDecimal bestAskAmount) {
    this.bestAskAmount = bestAskAmount;
  }

  public List<List<BigDecimal>> getAsks() {
    return asks;
  }

  public void setAsks(List<List<BigDecimal>> asks) {
    this.asks = asks;
  }

  @Override
  public String toString() {
    return "DeribitOrderBook{" +
            "timestamp=" + timestamp +
            ", stats=" + stats +
            ", state='" + state + '\'' +
            ", settlementPrice=" + settlementPrice +
            ", openInterest=" + openInterest +
            ", minPrice=" + minPrice +
            ", maxPrice=" + maxPrice +
            ", markPrice=" + markPrice +
            ", lastPrice=" + lastPrice +
            ", instrumentName='" + instrumentName + '\'' +
            ", indexPrice=" + indexPrice +
            ", funding8h=" + funding8h +
            ", greeks=" + greeks +
            ", currentFunding=" + currentFunding +
            ", changeId=" + changeId +
            ", bids=" + bids +
            ", bestBidPrice=" + bestBidPrice +
            ", bestBidAmount=" + bestBidAmount +
            ", bestAskPrice=" + bestAskPrice +
            ", bestAskAmount=" + bestAskAmount +
            ", asks=" + asks +
            '}';
  }
}
