package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitSummary {

  @JsonProperty("volume_usd")
  private BigDecimal volumeUsd;

  @JsonProperty("volume")
  private BigDecimal volume;

  @JsonProperty("underlying_price")
  private BigDecimal underlyingPrice;

  @JsonProperty("underlying_index")
  private String underlyingIndex;

  @JsonProperty("quote_currency")
  private String quoteCurrency;

  @JsonProperty("open_interest")
  private BigDecimal openInterest;

  @JsonProperty("mid_price")
  private BigDecimal midPrice;

  @JsonProperty("mark_price")
  private BigDecimal markPrice;

  @JsonProperty("low")
  private BigDecimal low;

  @JsonProperty("last")
  private BigDecimal last;

  @JsonProperty("instrument_name")
  private String instrumentName;

  @JsonProperty("high")
  private BigDecimal high;

  @JsonProperty("funding_8h")
  private BigDecimal funding8h;

  @JsonProperty("estimated_delivery_price")
  private BigDecimal estimatedDeliveryPrice;

  @JsonProperty("current_funding")
  private BigDecimal currentFunding;

  @JsonProperty("creation_timestamp")
  private long creationTimestamp;

  @JsonProperty("bid_price")
  private BigDecimal bidPrice;

  @JsonProperty("base_currency")
  private String baseCurrency;

  @JsonProperty("ask_price")
  private BigDecimal askPrice;

  public BigDecimal getVolumeUsd() {
    return volumeUsd;
  }

  public void setVolumeUsd(BigDecimal volumeUsd) {
    this.volumeUsd = volumeUsd;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public void setVolume(BigDecimal volume) {
    this.volume = volume;
  }

  public BigDecimal getUnderlyingPrice() {
    return underlyingPrice;
  }

  public void setUnderlyingPrice(BigDecimal underlyingPrice) {
    this.underlyingPrice = underlyingPrice;
  }

  public String getUnderlyingIndex() {
    return underlyingIndex;
  }

  public void setUnderlyingIndex(String underlyingIndex) {
    this.underlyingIndex = underlyingIndex;
  }

  public String getQuoteCurrency() {
    return quoteCurrency;
  }

  public void setQuoteCurrency(String quoteCurrency) {
    this.quoteCurrency = quoteCurrency;
  }

  public BigDecimal getOpenInterest() {
    return openInterest;
  }

  public void setOpenInterest(BigDecimal openInterest) {
    this.openInterest = openInterest;
  }

  public BigDecimal getMidPrice() {
    return midPrice;
  }

  public void setMidPrice(BigDecimal midPrice) {
    this.midPrice = midPrice;
  }

  public BigDecimal getMarkPrice() {
    return markPrice;
  }

  public void setMarkPrice(BigDecimal markPrice) {
    this.markPrice = markPrice;
  }

  public BigDecimal getLow() {
    return low;
  }

  public void setLow(BigDecimal low) {
    this.low = low;
  }

  public BigDecimal getLast() {
    return last;
  }

  public void setLast(BigDecimal last) {
    this.last = last;
  }

  public String getInstrumentName() {
    return instrumentName;
  }

  public void setInstrumentName(String instrumentName) {
    this.instrumentName = instrumentName;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public void setHigh(BigDecimal high) {
    this.high = high;
  }

  public BigDecimal getFunding8h() {
    return funding8h;
  }

  public void setFunding8h(BigDecimal funding8h) {
    this.funding8h = funding8h;
  }

  public BigDecimal getEstimatedDeliveryPrice() {
    return estimatedDeliveryPrice;
  }

  public void setEstimatedDeliveryPrice(BigDecimal estimatedDeliveryPrice) {
    this.estimatedDeliveryPrice = estimatedDeliveryPrice;
  }

  public BigDecimal getCurrentFunding() {
    return currentFunding;
  }

  public void setCurrentFunding(BigDecimal currentFunding) {
    this.currentFunding = currentFunding;
  }

  public long getCreationTimestamp() {
    return creationTimestamp;
  }

  public void setCreationTimestamp(long creationTimestamp) {
    this.creationTimestamp = creationTimestamp;
  }

  public BigDecimal getBidPrice() {
    return bidPrice;
  }

  public void setBidPrice(BigDecimal bidPrice) {
    this.bidPrice = bidPrice;
  }

  public String getBaseCurrency() {
    return baseCurrency;
  }

  public void setBaseCurrency(String baseCurrency) {
    this.baseCurrency = baseCurrency;
  }

  public BigDecimal getAskPrice() {
    return askPrice;
  }

  public void setAskPrice(BigDecimal askPrice) {
    this.askPrice = askPrice;
  }

  @Override
  public String toString() {
    return "DeribitSummary{"
        + "volumeUsd="
        + volumeUsd
        + ", volume="
        + volume
        + ", underlyingPrice="
        + underlyingPrice
        + ", underlyingIndex='"
        + underlyingIndex
        + '\''
        + ", quoteCurrency='"
        + quoteCurrency
        + '\''
        + ", openInterest="
        + openInterest
        + ", midPrice="
        + midPrice
        + ", markPrice="
        + markPrice
        + ", low="
        + low
        + ", last="
        + last
        + ", instrumentName='"
        + instrumentName
        + '\''
        + ", high="
        + high
        + ", funding8h="
        + funding8h
        + ", estimatedDeliveryPrice="
        + estimatedDeliveryPrice
        + ", currentFunding="
        + currentFunding
        + ", creationTimestamp="
        + creationTimestamp
        + ", bidPrice="
        + bidPrice
        + ", baseCurrency='"
        + baseCurrency
        + '\''
        + ", askPrice="
        + askPrice
        + '}';
  }
}
