package org.knowm.xchange.bittrex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BittrexCurrency {

  private final String currency;
  private final String currencyLong;
  private final int minConfirmations;
  private final BigDecimal txFee;
  private final boolean isActive;
  private final String coinType;
  private final String baseAddress;

  public BittrexCurrency(
      @JsonProperty("Currency") String currency,
      @JsonProperty("CurrencyLong") String currencyLong,
      @JsonProperty("MinConfirmation") int minConfirmations,
      @JsonProperty("TxFee") BigDecimal txFee,
      @JsonProperty("IsActive") boolean isActive,
      @JsonProperty("CoinType") String coinType,
      @JsonProperty("BaseAddress") String baseAddress) {

    this.currency = currency;
    this.currencyLong = currencyLong;
    this.minConfirmations = minConfirmations;
    this.txFee = txFee;
    this.isActive = isActive;
    this.coinType = coinType;
    this.baseAddress = baseAddress;
  }

  public String getCurrency() {

    return currency;
  }

  public String getCurrencyLong() {

    return currencyLong;
  }

  public int getMinConfirmations() {

    return minConfirmations;
  }

  public BigDecimal getTxFee() {

    return txFee;
  }

  public boolean isActive() {

    return isActive;
  }

  public String getCoinType() {

    return coinType;
  }

  public String getBaseAddress() {

    return baseAddress;
  }

  @Override
  public String toString() {

    return "BittrexCurrency [currency="
        + currency
        + ", currencyLong="
        + currencyLong
        + ", minConfirmations="
        + minConfirmations
        + ", txFee="
        + txFee
        + ", isActive="
        + isActive
        + ", coinType="
        + coinType
        + ", baseAddress="
        + baseAddress
        + "]";
  }
}
