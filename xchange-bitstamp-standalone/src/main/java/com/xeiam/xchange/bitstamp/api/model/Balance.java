package com.xeiam.xchange.bitstamp.api.model;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Matija Mazi <br/>
 * @created 4/20/12 7:30 PM
 */
public class Balance {

  @JsonProperty("usd_balance")
  private double usdBalance;
  @JsonProperty("btc_balance")
  private double btcBalance;
  @JsonProperty("usd_reserved")
  private double usdReserved;
  @JsonProperty("btc_reserved")
  private double btcReserved;
  @JsonProperty("usd_available")
  private double usdAvailable;
  @JsonProperty("btc_available")
  private double btcAvailable;

  private double fee;

  public double getUsdBalance() {

    return usdBalance;
  }

  public void setUsdBalance(double usdBalance) {

    this.usdBalance = usdBalance;
  }

  public double getBtcBalance() {

    return btcBalance;
  }

  public void setBtcBalance(double btcBalance) {

    this.btcBalance = btcBalance;
  }

  public double getUsdReserved() {

    return usdReserved;
  }

  public void setUsdReserved(double usdReserved) {

    this.usdReserved = usdReserved;
  }

  public double getBtcReserved() {

    return btcReserved;
  }

  public void setBtcReserved(double btcReserved) {

    this.btcReserved = btcReserved;
  }

  public double getUsdAvailable() {

    return usdAvailable;
  }

  public void setUsdAvailable(double usdAvailable) {

    this.usdAvailable = usdAvailable;
  }

  public double getBtcAvailable() {

    return btcAvailable;
  }

  public void setBtcAvailable(double btcAvailable) {

    this.btcAvailable = btcAvailable;
  }

  public double getFee() {

    return fee;
  }

  public void setFee(double fee) {

    this.fee = fee;
  }

  @Override
  public String toString() {

    return String.format("Balance{usdBalance=%s, btcBalance=%s, usdReserved=%s, btcReserved=%s, usdAvailable=%s, btcAvailable=%s, fee=%s}", usdBalance, btcBalance, usdReserved, btcReserved, usdAvailable, btcAvailable,
        fee);
  }
}
