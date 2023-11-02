package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.currency.Currency;

import java.math.BigDecimal;

public class AssetPortfolioMarginBalance {
  private final Currency currency;
  private final BigDecimal  totalWalletBalance ;
  private final BigDecimal crossMarginAsset ;
  private final BigDecimal crossMarginBorrowed ;
  private final BigDecimal crossMarginFree ;
  private final BigDecimal crossMarginInterest ;
  private final BigDecimal crossMarginLocked ;
  private final BigDecimal umWalletBalance;
  private final BigDecimal umUnrealizedPNL;
  private final BigDecimal cmWalletBalance ;
  private final BigDecimal cmUnrealizedPNL ;
  private final Long updateTime;


  public AssetPortfolioMarginBalance(
      @JsonProperty("asset") String asset,
      @JsonProperty("totalWalletBalance") BigDecimal totalWalletBalance,
      @JsonProperty("crossMarginAsset") BigDecimal crossMarginAsset,
      @JsonProperty("crossMarginBorrowed") BigDecimal crossMarginBorrowed,
      @JsonProperty("crossMarginFree") BigDecimal crossMarginFree,
      @JsonProperty("crossMarginInterest") BigDecimal crossMarginInterest,
      @JsonProperty("crossMarginLocked") BigDecimal crossMarginLocked,
      @JsonProperty("umWalletBalance") BigDecimal umWalletBalance,
      @JsonProperty("umUnrealizedPNL") BigDecimal umUnrealizedPNL,
      @JsonProperty("cmWalletBalance") BigDecimal cmWalletBalance,
      @JsonProperty("cmUnrealizedPNL") BigDecimal cmUnrealizedPNL,
      @JsonProperty("updateTime") Long updateTime) {
    this.currency = Currency.getInstance(asset);
    this.totalWalletBalance = totalWalletBalance;
    this.crossMarginAsset = crossMarginAsset;
    this.crossMarginBorrowed = crossMarginBorrowed;
    this.crossMarginFree = crossMarginFree;
    this.crossMarginInterest = crossMarginInterest;
    this.crossMarginLocked = crossMarginLocked;
    this.umWalletBalance = umWalletBalance;
    this.umUnrealizedPNL = umUnrealizedPNL;
    this.cmWalletBalance = cmWalletBalance;
    this.cmUnrealizedPNL = cmUnrealizedPNL;
    this.updateTime = updateTime;
  }

  public Currency getCurrency() {
    return currency;
  }
  public BigDecimal getTotal() {
    return totalWalletBalance;
  }

  public BigDecimal getAvailable() {
    return crossMarginFree;
  }

  public BigDecimal getLocked() {
    return crossMarginLocked;
  }


  @Override
  public String toString() {
    return "AssetPortfolioMarginBalance{"
        + "currency = '"
        + currency
        + '\''
        + ",totalWalletBalance = '"
        + totalWalletBalance
        + '\''
        + ",crossMarginAsset = '"
        + crossMarginAsset
        + '\''
        + ",crossMarginBorrowed = '"
        + crossMarginBorrowed
        + '\''
        + ",crossMarginFree = '"
        + crossMarginFree
        + '\''
        + ",crossMarginInterest = '"
        + crossMarginInterest
        + '\''
        + ",crossMarginLocked = '"
        + crossMarginLocked
        + '\''
        + ",umWalletBalance = '"
        + umWalletBalance
        + '\''
        + ",umUnrealizedPNL = '"
        + umUnrealizedPNL
        + '\''
        + ",cmWalletBalance = '"
        + cmWalletBalance
        + '\''
        + ",cmUnrealizedPNL = '"
        + cmUnrealizedPNL
        + '\''
        + ",updateTime = '"
        + updateTime
        + "}";
  }
}
