package org.knowm.xchange.ripple.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

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

  @JsonProperty("passive")
  private boolean passive;

  @JsonProperty("immediate_or_cancel")
  private boolean immediateOrCancel;

  @JsonProperty("fill_or_kill")
  private boolean fillOrKill;

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

  public boolean isPassive() {
    return passive;
  }

  public void setPassive(final boolean value) {
    passive = value;
  }

  public boolean isImmediateOrCancel() {
    return immediateOrCancel;
  }

  public void setImmediateOrCancel(final boolean value) {
    immediateOrCancel = value;
  }

  public boolean isFillOrKill() {
    return fillOrKill;
  }

  public void setFillOrKill(final boolean value) {
    fillOrKill = value;
  }

  @Override
  public String toString() {
    return String.format(
        "%s [type=%s, taker_pays=%s, taker_gets=%s, account=%s fee=%s, sequence=%d, cancel_sequence=%s, passive=%b, immediate_or_cancel=%b, fill_or_kill=%b]",
        getClass().getSimpleName(),
        getType(),
        getTakerPays(),
        getTakerGets(),
        account,
        fee,
        sequence,
        cancelSequence,
        passive,
        immediateOrCancel,
        fillOrKill);
  }
}
