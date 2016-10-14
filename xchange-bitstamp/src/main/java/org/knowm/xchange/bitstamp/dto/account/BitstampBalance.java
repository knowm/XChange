package org.knowm.xchange.bitstamp.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Matija Mazi
 */
public final class BitstampBalance {

  private final BigDecimal usdBalance;
  private final BigDecimal eurBalance;
  private final BigDecimal btcBalance;
  private final BigDecimal usdReserved;
  private final BigDecimal eurReserved;
  private final BigDecimal btcReserved;
  private final BigDecimal usdAvailable;
  private final BigDecimal eurAvailable;
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
  public BitstampBalance(@JsonProperty("usd_balance") BigDecimal usdBalance, @JsonProperty("eur_balance") BigDecimal eurBalance,
      @JsonProperty("btc_balance") BigDecimal btcBalance, @JsonProperty("usd_reserved") BigDecimal usdReserved,
      @JsonProperty("eur_reserved") BigDecimal eurReserved, @JsonProperty("btc_reserved") BigDecimal btcReserved,
      @JsonProperty("usd_available") BigDecimal usdAvailable, @JsonProperty("eur_available") BigDecimal eurAvailable,
      @JsonProperty("btc_available") BigDecimal btcAvailable, @JsonProperty("fee") BigDecimal fee, @JsonProperty("error") String error) {

    this.usdBalance = usdBalance;
    this.eurBalance = eurBalance;
    this.btcBalance = btcBalance;
    this.usdReserved = usdReserved;
    this.eurReserved = eurReserved;
    this.btcReserved = btcReserved;
    this.usdAvailable = usdAvailable;
    this.eurAvailable = eurAvailable;
    this.btcAvailable = btcAvailable;
    this.fee = fee;
    this.error = error;
  }

  public BigDecimal getUsdBalance() {

    return usdBalance;
  }

  public BigDecimal getEurBalance() {

    return eurBalance;
  }

  public BigDecimal getBtcBalance() {

    return btcBalance;
  }

  public BigDecimal getUsdReserved() {

    return usdReserved;
  }

  public BigDecimal getEurReserved() {

    return eurReserved;
  }

  public BigDecimal getBtcReserved() {

    return btcReserved;
  }

  public BigDecimal getUsdAvailable() {

    return usdAvailable;
  }

  public BigDecimal getEurAvailable() {

    return eurAvailable;
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

    return String.format(
        "Balance{usdBalance=%s, eurBalance=%s, btcBalance=%s, usdReserved=%s, eurReserved=%s,  btcReserved=%s, usdAvailable=%s, eurAvailable=%s, btcAvailable=%s, fee=%s}",
        usdBalance, eurBalance, btcBalance, usdReserved, eurReserved, btcReserved, usdAvailable, eurAvailable, btcAvailable, fee);
  }
}
