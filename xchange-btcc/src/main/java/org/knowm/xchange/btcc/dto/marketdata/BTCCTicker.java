package org.knowm.xchange.btcc.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BTCCTicker {

  @JsonProperty("BidPrice")
  private BigDecimal bidPrice;

  @JsonProperty("AskPrice")
  private BigDecimal askPrice;

  @JsonProperty("Open")
  private BigDecimal open;

  @JsonProperty("High")
  private BigDecimal high;

  @JsonProperty("Low")
  private BigDecimal low;

  @JsonProperty("Last")
  private BigDecimal last;

  @JsonProperty("LastQuantity")
  private BigDecimal lastQuantity;

  @JsonProperty("PrevCls")
  private BigDecimal prevClose;

  @JsonProperty("Volume")
  private BigDecimal volume;

  @JsonProperty("Volume24H")
  private BigDecimal volume24H;

  @JsonProperty("Timestamp")
  private long timestamp;

  @JsonProperty("ExecutionLimitDown")
  private BigDecimal executionLimitDown;

  @JsonProperty("ExecutionLimitUp")
  private BigDecimal executionLimitUp;

  public BigDecimal getBidPrice() {
    return bidPrice;
  }

  public void setBidPrice(BigDecimal bidPrice) {
    this.bidPrice = bidPrice;
  }

  public BigDecimal getAskPrice() {
    return askPrice;
  }

  public void setAskPrice(BigDecimal askPrice) {
    this.askPrice = askPrice;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public void setOpen(BigDecimal open) {
    this.open = open;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public void setHigh(BigDecimal high) {
    this.high = high;
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

  public BigDecimal getLastQuantity() {
    return lastQuantity;
  }

  public void setLastQuantity(BigDecimal lastQuantity) {
    this.lastQuantity = lastQuantity;
  }

  public BigDecimal getPrevClose() {
    return prevClose;
  }

  public void setPrevClose(BigDecimal precClose) {
    this.prevClose = precClose;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public void setVolume(BigDecimal volume) {
    this.volume = volume;
  }

  public BigDecimal getVolume24H() {
    return volume24H;
  }

  public void setVolume24H(BigDecimal volume24H) {
    this.volume24H = volume24H;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public BigDecimal getExecutionLimitDown() {
    return executionLimitDown;
  }

  public void setExecutionLimitDown(BigDecimal executionLimitDown) {
    this.executionLimitDown = executionLimitDown;
  }

  public BigDecimal getExecutionLimitUp() {
    return executionLimitUp;
  }

  public void setExecutionLimitUp(BigDecimal executionLimitUp) {
    this.executionLimitUp = executionLimitUp;
  }

  @Override
  public String toString() {
    return "BTCCTicker{"
        + "bidPrice="
        + bidPrice
        + ", askPrice="
        + askPrice
        + ", open="
        + open
        + ", high="
        + high
        + ", low="
        + low
        + ", last="
        + last
        + ", lastQuantity="
        + lastQuantity
        + ", prevClose="
        + prevClose
        + ", volume="
        + volume
        + ", volume24H="
        + volume24H
        + ", timestamp="
        + timestamp
        + ", executionLimitDown="
        + executionLimitDown
        + ", executionLimitUp="
        + executionLimitUp
        + '}';
  }
}
