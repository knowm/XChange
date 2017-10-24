package org.knowm.xchange.taurus.dto.account;

import java.math.BigDecimal;

import org.knowm.xchange.taurus.dto.TaurusBaseResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Matija Mazi
 */
public final class TaurusBalance extends TaurusBaseResponse {

  private final BigDecimal cadBalance;
  private final BigDecimal btcBalance;
  private final BigDecimal cadReserved;
  private final BigDecimal btcReserved;
  private final BigDecimal cadAvailable;
  private final BigDecimal btcAvailable;
  private final BigDecimal fee;

  public TaurusBalance(@JsonProperty("cad_balance") BigDecimal cadBalance, @JsonProperty("btc_balance") BigDecimal btcBalance,
      @JsonProperty("cad_reserved") BigDecimal cadReserved, @JsonProperty("btc_reserved") BigDecimal btcReserved,
      @JsonProperty("cad_available") BigDecimal cadAvailable, @JsonProperty("btc_available") BigDecimal btcAvailable,
      @JsonProperty("fee") BigDecimal fee, @JsonProperty("error") String error) {
    super(error);
    this.cadBalance = cadBalance;
    this.btcBalance = btcBalance;
    this.cadReserved = cadReserved;
    this.btcReserved = btcReserved;
    this.cadAvailable = cadAvailable;
    this.btcAvailable = btcAvailable;
    this.fee = fee;
  }

  public BigDecimal getCadBalance() {

    return cadBalance;
  }

  public BigDecimal getBtcBalance() {

    return btcBalance;
  }

  public BigDecimal getCadReserved() {

    return cadReserved;
  }

  public BigDecimal getBtcReserved() {

    return btcReserved;
  }

  public BigDecimal getCadAvailable() {

    return cadAvailable;
  }

  public BigDecimal getBtcAvailable() {

    return btcAvailable;
  }

  public BigDecimal getFee() {

    return fee;
  }

  @Override
  public String toString() {
    return String.format("Balance{cadBalance=%s, btcBalance=%s, cadReserved=%s, btcReserved=%s, cadAvailable=%s, btcAvailable=%s, fee=%s}",
        cadBalance, btcBalance, cadReserved, btcReserved, cadAvailable, btcAvailable, fee);
  }
}
