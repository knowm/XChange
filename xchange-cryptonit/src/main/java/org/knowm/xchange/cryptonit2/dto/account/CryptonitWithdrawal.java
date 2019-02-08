package org.knowm.xchange.cryptonit2.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class CryptonitWithdrawal {

  public final Integer id;
  public final Boolean success;
  public final String status;
  public final Object reason;
  public final Object error;

  public CryptonitWithdrawal(
      @JsonProperty("id") Integer id,
      @JsonProperty("success") Boolean success,
      @JsonProperty("status") String status,
      @JsonProperty("reason") Object reason,
      @JsonProperty("error") Object error) {
    this.id = id;
    this.success = success;
    this.status = status;
    this.reason = reason;
    this.error = error;
  }

  public Integer getId() {
    return id;
  }

  public String getStatus() {
    return status;
  }

  public Object getReason() {
    return reason;
  }

  public Boolean getSuccess() {
    return success;
  }

  @Override
  public String toString() {
    return "CryptonitWithdrawal{"
        + "id="
        + id
        + ", status='"
        + status
        + '\''
        + ", reason="
        + reason
        + '}';
  }

  public boolean hasError() {
    return this.status != null && this.status.equals("error");
  }
}
