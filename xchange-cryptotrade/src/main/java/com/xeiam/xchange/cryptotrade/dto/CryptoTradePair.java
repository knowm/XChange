package com.xeiam.xchange.cryptotrade.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = CryptoTradePairDeserializer.class)
public class CryptoTradePair extends CryptoTradeBaseResponse {

  private final String label;
  private final CryptoTradePairType type;
  private final BigDecimal minOrderAmount;
  private final int decimals;

  public CryptoTradePair(String label, CryptoTradePairType type, BigDecimal minOrderAmount, int decimals, String status, String error) {

    super(status, error);
    this.label = label;
    this.type = type;
    this.minOrderAmount = minOrderAmount;
    this.decimals = decimals;
  }

  public CryptoTradePair(String label, CryptoTradePairType type, BigDecimal minOrderAmount, int decimals) {

    super(null, null);
    this.label = label;
    this.type = type;
    this.minOrderAmount = minOrderAmount;
    this.decimals = decimals;
  }

  public String getLabel() {

    return label;
  }

  public CryptoTradePairType getType() {

    return type;
  }

  public BigDecimal getMinOrderAmount() {

    return minOrderAmount;
  }

  public int getDecimals() {

    return decimals;
  }

  @Override
  public String toString() {

    return String.format("CryptoTradePair [label='%s', type='%s', minOrderAmount='%s', decimals='%s']", getLabel(), getType(), getMinOrderAmount(),
        getDecimals());
  }

}
