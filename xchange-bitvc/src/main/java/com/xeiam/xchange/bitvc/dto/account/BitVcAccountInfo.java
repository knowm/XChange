package com.xeiam.xchange.bitvc.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitVcAccountInfo {

  private BigDecimal availableCny;
  private BigDecimal availableBtc;
  private BigDecimal availableLtc;
  private BigDecimal frozenCny;
  private BigDecimal frozenBtc;
  private BigDecimal frozenLtc;
  private BigDecimal loanCny;
  private BigDecimal loanBtc;
  private BigDecimal loanLtc;

  public BitVcAccountInfo(@JsonProperty("available_cny") final BigDecimal availableCnyDisplay,
      @JsonProperty("available_btc") final BigDecimal availableBtcDisplay, @JsonProperty("available_ltc") final BigDecimal availableLtcDisplay,
      @JsonProperty("frozen_cny") final BigDecimal frozenCnyDisplay, @JsonProperty("frozen_btc") final BigDecimal frozenBtcDisplay,
      @JsonProperty("frozen_ltc") final BigDecimal frozenLtcDisplay, @JsonProperty("loan_cny") final BigDecimal loanCnyDisplay,
      @JsonProperty("loan_btc") final BigDecimal loanBtcDisplay, @JsonProperty("loan_ltc") final BigDecimal loanLtcDisplay) {

    this.availableCny = availableCnyDisplay;
    this.availableBtc = availableBtcDisplay;
    this.availableLtc = availableLtcDisplay;
    this.frozenCny = frozenCnyDisplay;
    this.frozenBtc = frozenBtcDisplay;
    this.frozenLtc = frozenLtcDisplay;
    this.loanCny = loanCnyDisplay;
    this.loanBtc = loanBtcDisplay;
    this.loanLtc = loanLtcDisplay;
  }

  public BigDecimal getAvailableCny() {

    return availableCny;
  }

  public void setAvailableCny(BigDecimal availableCny) {

    this.availableCny = availableCny;
  }

  public BigDecimal getAvailableBtc() {

    return availableBtc;
  }

  public void setAvailableBtc(BigDecimal availableBtc) {

    this.availableBtc = availableBtc;
  }

  public BigDecimal getAvailableLtc() {

    return availableLtc;
  }

  public void setAvailableLtc(BigDecimal availableLtc) {

    this.availableLtc = availableLtc;
  }

  public BigDecimal getFrozenCny() {

    return frozenCny;
  }

  public void setFrozenCny(BigDecimal frozenCny) {

    this.frozenCny = frozenCny;
  }

  public BigDecimal getFrozenBtc() {

    return frozenBtc;
  }

  public void setFrozenBtc(BigDecimal frozenBtc) {

    this.frozenBtc = frozenBtc;
  }

  public BigDecimal getFrozenLtc() {

    return frozenLtc;
  }

  public void setFrozenLtc(BigDecimal frozenLtc) {

    this.frozenLtc = frozenLtc;
  }

  public BigDecimal getLoanCny() {

    return loanCny;
  }

  public void setLoanCny(BigDecimal loanCny) {

    this.loanCny = loanCny;
  }

  public BigDecimal getLoanBtc() {

    return loanBtc;
  }

  public void setLoanBtc(BigDecimal loanBtc) {

    this.loanBtc = loanBtc;
  }

  public BigDecimal getLoanLtc() {

    return loanLtc;
  }

  public void setLoanLtc(BigDecimal loanLtc) {

    this.loanLtc = loanLtc;
  }

  @Override
  public String toString() {

    return "BitVcAccountInfo [availableCny=" + availableCny + ", availableBtc=" + availableBtc + ", availableLtc=" + availableLtc + ", frozenCny="
        + frozenCny + ", frozenBtc=" + frozenBtc + ", frozenLtc=" + frozenLtc + ", loanCny=" + loanCny + ", loanBtc=" + loanBtc + ", loanLtc="
        + loanLtc + "]";
  }

}
