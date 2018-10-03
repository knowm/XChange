package org.knowm.xchange.ripple.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class RippleOrderCancelResponseBody {
  @JsonProperty("account")
  private String account;

  @JsonProperty("fee")
  private BigDecimal fee;

  @JsonProperty("offer_sequence")
  private long offerSequence;

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

  public long getOfferSequence() {
    return offerSequence;
  }

  public void setOfferSequence(final long value) {
    offerSequence = value;
  }

  public long getSequence() {
    return sequence;
  }

  public void setSequence(final long value) {
    sequence = value;
  }

  @Override
  public String toString() {
    return String.format(
        "%s [account=%s fee=%s, offerSequence=%d sequence=%d]",
        getClass().getSimpleName(), account, fee, offerSequence, sequence);
  }
}
