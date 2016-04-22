package org.knowm.xchange.huobi.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HuobiAccountInfo {

  private final BigDecimal availableCny;
  private final BigDecimal availableBtc;
  private final BigDecimal availableLtc;
  private final BigDecimal frozenCny;
  private final BigDecimal frozenBtc;
  private final BigDecimal frozenLtc;
  private final BigDecimal loanCny;
  private final BigDecimal loanBtc;
  private final BigDecimal loanLtc;

  private final String msg;
  private final String result;

  public HuobiAccountInfo(@JsonProperty("available_cny_display") final BigDecimal availableCnyDisplay,
      @JsonProperty("available_btc_display") final BigDecimal availableBtcDisplay,
      @JsonProperty("available_ltc_display") final BigDecimal availableLtcDisplay,
      @JsonProperty("frozen_cny_display") final BigDecimal frozenCnyDisplay, @JsonProperty("frozen_btc_display") final BigDecimal frozenBtcDisplay,
      @JsonProperty("frozen_ltc_display") final BigDecimal frozenLtcDisplay, @JsonProperty("loan_cny_display") final BigDecimal loanCnyDisplay,
      @JsonProperty("loan_btc_display") final BigDecimal loanBtcDisplay, @JsonProperty("loan_ltc_display") final BigDecimal loanLtcDisplay,
      @JsonProperty("msg") String msg, @JsonProperty("result") String result) {

    this.availableCny = availableCnyDisplay;
    this.availableBtc = availableBtcDisplay;
    this.availableLtc = availableLtcDisplay;
    this.frozenCny = frozenCnyDisplay;
    this.frozenBtc = frozenBtcDisplay;
    this.frozenLtc = frozenLtcDisplay;
    this.loanCny = loanCnyDisplay;
    this.loanBtc = loanBtcDisplay;
    this.loanLtc = loanLtcDisplay;

    this.msg = msg;
    this.result = result;
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

  public String getMsg() {
    return msg;
  }

  public String getResult() {
    return result;
  }
}