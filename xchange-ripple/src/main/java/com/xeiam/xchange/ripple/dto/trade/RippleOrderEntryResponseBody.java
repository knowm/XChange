package com.xeiam.xchange.ripple.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RippleOrderEntryResponseBody extends RippleOrderEntryRequestBody {
  @JsonProperty("account")
  private String account;

  @JsonProperty("fee")
  private BigDecimal fee;

  @JsonProperty("sequence")
  private long sequence;

  public String getAccount() {
    return account;
  }

  public void setAccount(final String value) {
    account = value;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public void setFee(final BigDecimal value) {
    fee = value;
  }

  public long getSequence() {
    return sequence;
  }

  public void setSequence(final long value) {
    sequence = value;
  }

  @Override
  public String toString() {
    return String.format("%s [type=%s, taker_pays=%s, taker_gets=%s, account=%s fee=%s, sequence=%s]", getClass().getSimpleName(), getType(), getTakerPays(),
        getTakerGets(), account, fee, sequence);
  }
}
