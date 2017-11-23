package org.knowm.xchange.cryptopia.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class CryptopiaCurrency {

  private final long id;
  private final String name;
  private final String symbol;
  private final String algorithm;
  private final BigDecimal withdrawFee;
  private final BigDecimal minWithdraw;
  private final BigDecimal maxWithdraw;
  private final BigDecimal minBaseTrade;
  private final boolean isTipEnabled;
  private final BigDecimal minTip;
  private final long depositConfirmations;
  private final String status;
  private final String statusMessage;
  private final String listingStatus;

  public CryptopiaCurrency(@JsonProperty("Id") long id, @JsonProperty("Name") String name, @JsonProperty("Symbol") String symbol, @JsonProperty("Algorithm") String algorithm,
      @JsonProperty("WithdrawFee") BigDecimal withdrawFee, @JsonProperty("MinWithdraw") BigDecimal minWithdraw, @JsonProperty("MaxWithdraw") BigDecimal maxWithdraw,
      @JsonProperty("MinBaseTrade") BigDecimal minBaseTrade, @JsonProperty("IsTipEnabled") boolean isTipEnabled, @JsonProperty("MinTip") BigDecimal minTip,
      @JsonProperty("DepositConfirmations") long depositConfirmations, @JsonProperty("Status") String status, @JsonProperty("StatusMessage") String statusMessage,
      @JsonProperty("ListingStatus") String listingStatus) {
    this.id = id;
    this.name = name;
    this.symbol = symbol;
    this.algorithm = algorithm;
    this.withdrawFee = withdrawFee;
    this.minWithdraw = minWithdraw;
    this.maxWithdraw = maxWithdraw;
    this.minBaseTrade = minBaseTrade;
    this.isTipEnabled = isTipEnabled;
    this.minTip = minTip;
    this.depositConfirmations = depositConfirmations;
    this.status = status;
    this.statusMessage = statusMessage;
    this.listingStatus = listingStatus;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getAlgorithm() {
    return algorithm;
  }

  public BigDecimal getWithdrawFee() {
    return withdrawFee;
  }

  public BigDecimal getMinWithdraw() {
    return minWithdraw;
  }

  public BigDecimal getMaxWithdraw() {
    return maxWithdraw;
  }

  public BigDecimal getMinBaseTrade() {
    return minBaseTrade;
  }

  public boolean isTipEnabled() {
    return isTipEnabled;
  }

  public BigDecimal getMinTip() {
    return minTip;
  }

  public long getDepositConfirmations() {
    return depositConfirmations;
  }

  public String getStatus() {
    return status;
  }

  public String getStatusMessage() {
    return statusMessage;
  }

  public String getListingStatus() {
    return listingStatus;
  }

  @Override
  public String toString() {
    return "CryptopiaCurrency{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", symbol='" + symbol + '\'' +
        ", algorithm='" + algorithm + '\'' +
        ", withdrawFee=" + withdrawFee +
        ", minWithdraw=" + minWithdraw +
        ", maxWithdraw=" + maxWithdraw +
        ", minBaseTrade=" + minBaseTrade +
        ", isTipEnabled=" + isTipEnabled +
        ", minTip=" + minTip +
        ", depositConfirmations=" + depositConfirmations +
        ", status='" + status + '\'' +
        ", statusMessage='" + statusMessage + '\'' +
        ", listingStatus='" + listingStatus + '\'' +
        '}';
  }
}
