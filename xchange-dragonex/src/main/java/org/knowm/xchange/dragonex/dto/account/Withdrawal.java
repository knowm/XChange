package org.knowm.xchange.dragonex.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class Withdrawal {

  public final long withdrawId;
  public final long uid;
  public final long coinId;
  public final long addressId;
  public final String addressName;
  public final String address;
  public final BigDecimal volume;
  public final long status;

  public Withdrawal(
      @JsonProperty("withdraw_id") long withdrawId,
      @JsonProperty("uid") long uid,
      @JsonProperty("coin_id") long coinId,
      @JsonProperty("addr_id") long addressId,
      @JsonProperty("addr_name") String addressName,
      @JsonProperty("addr") String address,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("status") long status) {
    this.withdrawId = withdrawId;
    this.uid = uid;
    this.coinId = coinId;
    this.addressId = addressId;
    this.addressName = addressName;
    this.address = address;
    this.volume = volume;
    this.status = status;
  }

  @Override
  public String toString() {
    return "Withdrawal [withdrawId="
        + withdrawId
        + ", uid="
        + uid
        + ", coinId="
        + coinId
        + ", addressId="
        + addressId
        + ", "
        + (addressName != null ? "addressName=" + addressName + ", " : "")
        + (address != null ? "address=" + address + ", " : "")
        + (volume != null ? "volume=" + volume + ", " : "")
        + "status="
        + status
        + "]";
  }
}
