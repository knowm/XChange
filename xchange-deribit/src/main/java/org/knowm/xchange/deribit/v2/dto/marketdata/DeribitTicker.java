package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitTicker {

  @JsonProperty("ask_iv")
  private BigDecimal askIv;

  @JsonProperty("best_ask_amount")
  private BigDecimal bestAskAmount;

  @JsonProperty("best_ask_price")
  private BigDecimal bestAskPrice;

  @JsonProperty("best_bid_amount")
  private BigDecimal bestBidAmount;

  @JsonProperty("best_bid_price")
  private BigDecimal bestBidPrice;

  @JsonProperty("bid_iv")
  private BigDecimal bidIv;

  @JsonProperty("greeks")
  private DeribitGreeks greeks;

  @JsonProperty("current_funding")
  private BigDecimal currentFunding;

  @JsonProperty("funding_8h")
  private BigDecimal funding8h;

  @JsonProperty("index_price")
  private BigDecimal indexPrice;

  @JsonProperty("instrument_name")
  private String instrumentName;

  @JsonProperty("interest_rate")
  private BigDecimal interestRate;

  @JsonProperty("last_price")
  private BigDecimal lastPrice;

  @JsonProperty("mark_iv")
  private BigDecimal markIv;

  @JsonProperty("mark_price")
  private BigDecimal markPrice;

  @JsonProperty("max_price")
  private BigDecimal maxPrice;

  @JsonProperty("min_price")
  private BigDecimal minPrice;

  @JsonProperty("open_interest")
  private BigDecimal openInterest;

  @JsonProperty("settlement_price")
  private BigDecimal settlementPrice;

  @JsonProperty("state")
  private String state;

  @JsonProperty("stats")
  private DeribitStats stats;

  @JsonProperty("timestamp")
  private long timestamp;

  @JsonProperty("underlying_index")
  private String underlyingIndex;

  @JsonProperty("underlying_price")
  private BigDecimal underlyingPrice;

  public BigDecimal getAskIv() {
    return askIv;
  }

  public void setAskIv(BigDecimal askIv) {
    this.askIv = askIv;
  }

  public BigDecimal getBestAskAmount() {
    return bestAskAmount;
  }

  public void setBestAskAmount(BigDecimal bestAskAmount) {
    this.bestAskAmount = bestAskAmount;
  }

  public BigDecimal getBestAskPrice() {
    return bestAskPrice;
  }

  public void setBestAskPrice(BigDecimal bestAskPrice) {
    this.bestAskPrice = bestAskPrice;
  }

  public BigDecimal getBestBidAmount() {
    return bestBidAmount;
  }

  public void setBestBidAmount(BigDecimal bestBidAmount) {
    this.bestBidAmount = bestBidAmount;
  }

  public BigDecimal getBestBidPrice() {
    return bestBidPrice;
  }

  public void setBestBidPrice(BigDecimal bestBidPrice) {
    this.bestBidPrice = bestBidPrice;
  }

  public BigDecimal getBidIv() {
    return bidIv;
  }

  public void setBidIv(BigDecimal bidIv) {
    this.bidIv = bidIv;
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

  public BigDecimal getFunding8h() {
    return funding8h;
  }

  public void setFunding8h(BigDecimal funding8h) {
    this.funding8h = funding8h;
  }

  public BigDecimal getIndexPrice() {
    return indexPrice;
  }

  public void setIndexPrice(BigDecimal indexPrice) {
    this.indexPrice = indexPrice;
  }

  public String getInstrumentName() {
    return instrumentName;
  }

  public void setInstrumentName(String instrumentName) {
    this.instrumentName = instrumentName;
  }

  public BigDecimal getInterestRate() {
    return interestRate;
  }

  public void setInterestRate(BigDecimal interestRate) {
    this.interestRate = interestRate;
  }

  public BigDecimal getLastPrice() {
    return lastPrice;
  }

  public void setLastPrice(BigDecimal lastPrice) {
    this.lastPrice = lastPrice;
  }

  public BigDecimal getMarkIv() {
    return markIv;
  }

  public void setMarkIv(BigDecimal markIv) {
    this.markIv = markIv;
  }

  public BigDecimal getMarkPrice() {
    return markPrice;
  }

  public void setMarkPrice(BigDecimal markPrice) {
    this.markPrice = markPrice;
  }

  public BigDecimal getMaxPrice() {
    return maxPrice;
  }

  public void setMaxPrice(BigDecimal maxPrice) {
    this.maxPrice = maxPrice;
  }

  public BigDecimal getMinPrice() {
    return minPrice;
  }

  public void setMinPrice(BigDecimal minPrice) {
    this.minPrice = minPrice;
  }

  public BigDecimal getOpenInterest() {
    return openInterest;
  }

  public void setOpenInterest(BigDecimal openInterest) {
    this.openInterest = openInterest;
  }

  public BigDecimal getSettlementPrice() {
    return settlementPrice;
  }

  public void setSettlementPrice(BigDecimal settlementPrice) {
    this.settlementPrice = settlementPrice;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public DeribitStats getStats() {
    return stats;
  }

  public void setStats(DeribitStats stats) {
    this.stats = stats;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public String getUnderlyingIndex() {
    return underlyingIndex;
  }

  public void setUnderlyingIndex(String underlyingIndex) {
    this.underlyingIndex = underlyingIndex;
  }

  public BigDecimal getUnderlyingPrice() {
    return underlyingPrice;
  }

  public void setUnderlyingPrice(BigDecimal underlyingPrice) {
    this.underlyingPrice = underlyingPrice;
  }

  @Override
  public String toString() {
    return "DeribitTicker{"
        + "askIv="
        + askIv
        + ", bestAskAmount="
        + bestAskAmount
        + ", bestAskPrice="
        + bestAskPrice
        + ", bestBidAmount="
        + bestBidAmount
        + ", bestBidPrice="
        + bestBidPrice
        + ", bidIv="
        + bidIv
        + ", greeks="
        + greeks
        + ", currentFunding="
        + currentFunding
        + ", funding8h="
        + funding8h
        + ", indexPrice="
        + indexPrice
        + ", instrumentName='"
        + instrumentName
        + '\''
        + ", interestRate="
        + interestRate
        + ", lastPrice="
        + lastPrice
        + ", markIv="
        + markIv
        + ", markPrice="
        + markPrice
        + ", maxPrice="
        + maxPrice
        + ", minPrice="
        + minPrice
        + ", openInterest="
        + openInterest
        + ", settlementPrice="
        + settlementPrice
        + ", state='"
        + state
        + '\''
        + ", stats="
        + stats
        + ", timestamp="
        + timestamp
        + ", underlyingIndex='"
        + underlyingIndex
        + '\''
        + ", underlyingPrice="
        + underlyingPrice
        + '}';
  }
}
