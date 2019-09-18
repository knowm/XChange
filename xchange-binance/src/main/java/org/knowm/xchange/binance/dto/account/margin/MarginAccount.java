package org.knowm.xchange.binance.dto.account.margin;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public class MarginAccount {

  private Boolean borrowEnabled;
  private BigDecimal marginLevel;
  private BigDecimal totalAssetOfBtc;
  private BigDecimal totalLiabilityOfBtc;
  private BigDecimal totalNetAssetOfBtc;
  private Boolean tradeEnabled;
  private Boolean transferEnabled;
  private List<MarginAssetBalance> userAssets;

  public MarginAccount(
          @JsonProperty("borrowEnabled") Boolean borrowEnabled,
          @JsonProperty("marginLevel") BigDecimal marginLevel,
          @JsonProperty("totalAssetOfBtc") BigDecimal totalAssetOfBtc,
          @JsonProperty("totalLiabilityOfBtc") BigDecimal totalLiabilityOfBtc,
          @JsonProperty("totalNetAssetOfBtc") BigDecimal totalNetAssetOfBtc,
          @JsonProperty("tradeEnabled") Boolean tradeEnabled,
          @JsonProperty("transferEnabled") Boolean transferEnabled,
          @JsonProperty("userAssets") List<MarginAssetBalance> userAssets) {
    this.borrowEnabled = borrowEnabled;
    this.marginLevel = marginLevel;
    this.totalAssetOfBtc = totalAssetOfBtc;
    this.totalLiabilityOfBtc = totalLiabilityOfBtc;
    this.totalNetAssetOfBtc = totalNetAssetOfBtc;
    this.tradeEnabled = tradeEnabled;
    this.transferEnabled = transferEnabled;
    this.userAssets = userAssets;
  }

  public Boolean getBorrowEnabled() {
    return borrowEnabled;
  }

  public void setBorrowEnabled(Boolean borrowEnabled) {
    this.borrowEnabled = borrowEnabled;
  }

  public BigDecimal getMarginLevel() {
    return marginLevel;
  }

  public void setMarginLevel(BigDecimal marginLevel) {
    this.marginLevel = marginLevel;
  }

  public BigDecimal getTotalAssetOfBtc() {
    return totalAssetOfBtc;
  }

  public void setTotalAssetOfBtc(BigDecimal totalAssetOfBtc) {
    this.totalAssetOfBtc = totalAssetOfBtc;
  }

  public BigDecimal getTotalLiabilityOfBtc() {
    return totalLiabilityOfBtc;
  }

  public void setTotalLiabilityOfBtc(BigDecimal totalLiabilityOfBtc) {
    this.totalLiabilityOfBtc = totalLiabilityOfBtc;
  }

  public BigDecimal getTotalNetAssetOfBtc() {
    return totalNetAssetOfBtc;
  }

  public void setTotalNetAssetOfBtc(BigDecimal totalNetAssetOfBtc) {
    this.totalNetAssetOfBtc = totalNetAssetOfBtc;
  }

  public Boolean getTradeEnabled() {
    return tradeEnabled;
  }

  public void setTradeEnabled(Boolean tradeEnabled) {
    this.tradeEnabled = tradeEnabled;
  }

  public Boolean getTransferEnabled() {
    return transferEnabled;
  }

  public void setTransferEnabled(Boolean transferEnabled) {
    this.transferEnabled = transferEnabled;
  }

  public List<MarginAssetBalance> getUserAssets() {
    return userAssets;
  }

  public void setUserAssets(List<MarginAssetBalance> userAssets) {
    this.userAssets = userAssets;
  }
}
