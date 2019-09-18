package org.knowm.xchange.binance.dto.account.margin;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.currency.Currency;

import java.math.BigDecimal;

public class MarginAssetBalance {

  private Currency asset;
  private BigDecimal borrowed;
  private BigDecimal free;
  private BigDecimal interest;
  private BigDecimal locked;
  private BigDecimal netAsset;

  public MarginAssetBalance(
          @JsonProperty("asset") String asset,
          @JsonProperty("borrowed") BigDecimal borrowed,
          @JsonProperty("free") BigDecimal free,
          @JsonProperty("interest") BigDecimal interest,
          @JsonProperty("locked") BigDecimal locked,
          @JsonProperty("netAsset") BigDecimal netAsset) {
    this.asset = Currency.getInstance(asset);
    this.borrowed = borrowed;
    this.free = free;
    this.interest = interest;
    this.locked = locked;
    this.netAsset = netAsset;
  }

  public Currency getAsset() {
    return asset;
  }

  public void setAsset(Currency asset) {
    this.asset = asset;
  }

  public BigDecimal getBorrowed() {
    return borrowed;
  }

  public void setBorrowed(BigDecimal borrowed) {
    this.borrowed = borrowed;
  }

  public BigDecimal getFree() {
    return free;
  }

  public void setFree(BigDecimal free) {
    this.free = free;
  }

  public BigDecimal getInterest() {
    return interest;
  }

  public void setInterest(BigDecimal interest) {
    this.interest = interest;
  }

  public BigDecimal getLocked() {
    return locked;
  }

  public void setLocked(BigDecimal locked) {
    this.locked = locked;
  }

  public BigDecimal getNetAsset() {
    return netAsset;
  }

  public void setNetAsset(BigDecimal netAsset) {
    this.netAsset = netAsset;
  }
}
