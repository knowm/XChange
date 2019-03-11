package org.knowm.xchange.ripple.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class RippleCommon {
  @JsonProperty("hash")
  protected String hash;

  @JsonProperty("ledger")
  protected long ledger;

  @JsonProperty("success")
  protected Boolean success;

  @JsonProperty("state")
  protected String state;

  @JsonProperty("validated")
  protected Boolean validated;

  public final String getHash() {
    return hash;
  }

  public final void setHash(final String value) {
    hash = value;
  }

  public final long getLedger() {
    return ledger;
  }

  public final void setLedger(final long value) {
    ledger = value;
  }

  public final String getState() {
    return state;
  }

  public final void setState(final String value) {
    state = value;
  }

  public final Boolean isValidated() {
    return validated;
  }

  public final void setValidated(final Boolean value) {
    validated = value;
  }

  public final Boolean isSuccess() {
    return success;
  }

  public final void setSuccess(final Boolean value) {
    success = value;
  }
}
