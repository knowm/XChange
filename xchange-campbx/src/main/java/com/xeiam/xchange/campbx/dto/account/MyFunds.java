package com.xeiam.xchange.campbx.dto.account;

/**
 * @author Matija Mazi 
 */
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.campbx.dto.CampBXResponse;

public final class MyFunds extends CampBXResponse {

  @JsonProperty("Total USD")
  private BigDecimal totalUSD;
  @JsonProperty("Total BTC")
  private BigDecimal totalBTC;
  @JsonProperty("Liquid USD")
  private BigDecimal liquidUSD;
  @JsonProperty("Liquid BTC")
  private BigDecimal liquidBTC;
  @JsonProperty("Margin Account USD")
  private BigDecimal marginAccountUSD;
  @JsonProperty("Margin Account BTC")
  private BigDecimal marginAccountBTC;

  public BigDecimal getTotalUSD() {

    return totalUSD;
  }

  public void setTotalUSD(BigDecimal totalUSD) {

    this.totalUSD = totalUSD;
  }

  public BigDecimal getTotalBTC() {

    return totalBTC;
  }

  public void setTotalBTC(BigDecimal totalBTC) {

    this.totalBTC = totalBTC;
  }

  public BigDecimal getLiquidUSD() {

    return liquidUSD;
  }

  public void setLiquidUSD(BigDecimal liquidUSD) {

    this.liquidUSD = liquidUSD;
  }

  public BigDecimal getLiquidBTC() {

    return liquidBTC;
  }

  public void setLiquidBTC(BigDecimal liquidBTC) {

    this.liquidBTC = liquidBTC;
  }

  public BigDecimal getMarginAccountUSD() {

    return marginAccountUSD;
  }

  public void setMarginAccountUSD(BigDecimal marginAccountUSD) {

    this.marginAccountUSD = marginAccountUSD;
  }

  public BigDecimal getMarginAccountBTC() {

    return marginAccountBTC;
  }

  public void setMarginAccountBTC(BigDecimal marginAccountBTC) {

    this.marginAccountBTC = marginAccountBTC;
  }

  @Override
  public String toString() {

    return "MyFunds [totalUSD=" + totalUSD + ", totalBTC=" + totalBTC + ", liquidUSD=" + liquidUSD + ", liquidBTC=" + liquidBTC + ", marginAccountUSD=" + marginAccountUSD + ", marginAccountBTC="
        + marginAccountBTC + ", getSuccess()=" + getSuccess() + ", getInfo()=" + getInfo() + ", getError()=" + getError() + "]";
  }

}