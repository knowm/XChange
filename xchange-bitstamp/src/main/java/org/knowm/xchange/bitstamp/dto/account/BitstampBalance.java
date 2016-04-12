package org.knowm.xchange.bitstamp.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Matija Mazi
 */
public final class BitstampBalance {

  private final BigDecimal usdBalance;
  private final BigDecimal btcBalance;
  private final BigDecimal usdReserved;
  private final BigDecimal btcReserved;
  private final BigDecimal usdAvailable;
  private final BigDecimal btcAvailable;
  private final BigDecimal fee;
  private final String error;

  /**
   * Constructor
   * 
   * @param usdBalance
   * @param btcBalance
   * @param usdReserved
   * @param btcReserved
   * @param usdAvailable
   * @param btcAvailable
   * @param fee
   */
  public BitstampBalance(@JsonProperty("usd_balance") BigDecimal usdBalance, @JsonProperty("btc_balance") BigDecimal btcBalance,
      @JsonProperty("usd_reserved") BigDecimal usdReserved, @JsonProperty("btc_reserved") BigDecimal btcReserved,
      @JsonProperty("usd_available") BigDecimal usdAvailable, @JsonProperty("btc_available") BigDecimal btcAvailable,
      @JsonProperty("fee") BigDecimal fee, @JsonProperty("error") String error) {

    this.usdBalance = usdBalance;
    this.btcBalance = btcBalance;
    this.usdReserved = usdReserved;
    this.btcReserved = btcReserved;
    this.usdAvailable = usdAvailable;
    this.btcAvailable = btcAvailable;
    this.fee = fee;
    this.error = error;
  }

  public BigDecimal getUsdBalance() {

    return usdBalance;
  }

  public BigDecimal getBtcBalance() {

    return btcBalance;
  }

  public BigDecimal getUsdReserved() {

    return usdReserved;
  }

  public BigDecimal getBtcReserved() {

    return btcReserved;
  }

  public BigDecimal getUsdAvailable() {

    return usdAvailable;
  }

  public BigDecimal getBtcAvailable() {

    return btcAvailable;
  }

  public BigDecimal getFee() {

    return fee;
  }

  public String getError() {

    return error;
  }

  @Override
  public String toString() {

    return String.format("Balance{usdBalance=%s, btcBalance=%s, usdReserved=%s, btcReserved=%s, usdAvailable=%s, btcAvailable=%s, fee=%s}",
        usdBalance, btcBalance, usdReserved, btcReserved, usdAvailable, btcAvailable, fee);
  }
}
