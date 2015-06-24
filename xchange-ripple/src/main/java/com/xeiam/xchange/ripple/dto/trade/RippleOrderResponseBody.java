package com.xeiam.xchange.ripple.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RippleOrderResponseBody extends RippleOrderEntryRequestBody {
  @JsonProperty("account")
  private String account;

  @JsonProperty("fee")
  private BigDecimal fee;

  @JsonProperty("status")
  private String status;

  @JsonProperty("sequence")
  private long sequence;

  @JsonProperty("cancel_sequence")
  private long cancelSequence;

  public String getAccount() {
    return account;
  }

  public void setAccount(final String value) {
    account = value;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(final String value) {
    status = value;
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

  public long getCancelSequence() {
    return cancelSequence;
  }

  public void setCancelSequence(final long value) {
    cancelSequence = value;
  }

  @Override
  public String toString() {
    return String.format("%s [type=%s, taker_pays=%s, taker_gets=%s, account=%s fee=%s, sequence=%d, cancel_sequence=%s]",
        getClass().getSimpleName(), getType(), getTakerPays(), getTakerGets(), account, fee, sequence, cancelSequence);
  }
}
