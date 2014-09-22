package com.xeiam.xchange.bitvc.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitVcAccountInfo {

  private final BigDecimal availableCny;
  private final BigDecimal availableBtc;
  private final BigDecimal availableLtc;
  private final BigDecimal frozenCny;
  private final BigDecimal frozenBtc;
  private final BigDecimal frozenLtc;
  private final BigDecimal loanCny;
  private final BigDecimal loanBtc;
  private final BigDecimal loanLtc;

  public BitVcAccountInfo(@JsonProperty("available_cny") final BigDecimal availableCnyDisplay, @JsonProperty("available_btc") final BigDecimal availableBtcDisplay,
      @JsonProperty("available_ltc") final BigDecimal availableLtcDisplay, @JsonProperty("frozen_cny") final BigDecimal frozenCnyDisplay,
      @JsonProperty("frozen_btc") final BigDecimal frozenBtcDisplay, @JsonProperty("frozen_ltc") final BigDecimal frozenLtcDisplay, @JsonProperty("loan_cny") final BigDecimal loanCnyDisplay,
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

  public BigDecimal getAvailableCnyDisplay() {

    return availableCny;
  }

  public BigDecimal getAvailableBtcDisplay() {

    return availableBtc;
  }

  public BigDecimal getAvailableLtcDisplay() {

    return availableLtc;
  }

  public BigDecimal getFrozenCnyDisplay() {

    return frozenCny;
  }

  public BigDecimal getFrozenBtcDisplay() {

    return frozenBtc;
  }

  public BigDecimal getFrozenLtcDisplay() {

    return frozenLtc;
  }

  public BigDecimal getLoanCnyDisplay() {

    return loanCny;
  }

  public BigDecimal getLoanBtcDisplay() {

    return loanBtc;
  }

  public BigDecimal getLoanLtcDisplay() {

    return loanLtc;
  }

}
