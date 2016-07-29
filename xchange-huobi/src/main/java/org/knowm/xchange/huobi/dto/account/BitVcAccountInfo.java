package org.knowm.xchange.huobi.dto.account;

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
  private final String message;

  public BitVcAccountInfo(@JsonProperty("available_cny") final BigDecimal availableCnyDisplay,
      @JsonProperty("available_btc") final BigDecimal availableBtcDisplay, @JsonProperty("available_ltc") final BigDecimal availableLtcDisplay,
      @JsonProperty("frozen_cny") final BigDecimal frozenCnyDisplay, @JsonProperty("frozen_btc") final BigDecimal frozenBtcDisplay,
      @JsonProperty("frozen_ltc") final BigDecimal frozenLtcDisplay, @JsonProperty("loan_cny") final BigDecimal loanCnyDisplay,
      @JsonProperty("loan_btc") final BigDecimal loanBtcDisplay, @JsonProperty("loan_ltc") final BigDecimal loanLtcDisplay,
      @JsonProperty("message") final String message) {

    this.availableCny = availableCnyDisplay;
    this.availableBtc = availableBtcDisplay;
    this.availableLtc = availableLtcDisplay;
    this.frozenCny = frozenCnyDisplay;
    this.frozenBtc = frozenBtcDisplay;
    this.frozenLtc = frozenLtcDisplay;
    this.loanCny = loanCnyDisplay;
    this.loanBtc = loanBtcDisplay;
    this.loanLtc = loanLtcDisplay;
    this.message = message;
  }

  public BigDecimal getAvailableCnyDisplay() {

    return availableCny == null ? BigDecimal.ZERO : availableCny;
  }

  public BigDecimal getAvailableBtcDisplay() {

    return availableBtc == null ? BigDecimal.ZERO : availableBtc;
  }

  public BigDecimal getAvailableLtcDisplay() {

    return availableLtc == null ? BigDecimal.ZERO : availableLtc;
  }

  public BigDecimal getFrozenCnyDisplay() {

    return frozenCny == null ? BigDecimal.ZERO : frozenCny;
  }

  public BigDecimal getFrozenBtcDisplay() {

    return frozenBtc == null ? BigDecimal.ZERO : frozenBtc;
  }

  public BigDecimal getFrozenLtcDisplay() {

    return frozenLtc == null ? BigDecimal.ZERO : frozenLtc;
  }

  public BigDecimal getLoanCnyDisplay() {

    return loanCny == null ? BigDecimal.ZERO : loanCny;
  }

  public BigDecimal getLoanBtcDisplay() {

    return loanBtc == null ? BigDecimal.ZERO : loanBtc;
  }

  public BigDecimal getLoanLtcDisplay() {

    return loanLtc == null ? BigDecimal.ZERO : loanLtc;
  }

  public String getMessage() {

    return message;
  }

}
