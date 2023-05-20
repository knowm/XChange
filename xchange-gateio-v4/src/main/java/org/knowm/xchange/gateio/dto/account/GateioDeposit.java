package org.knowm.xchange.gateio.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class GateioDeposit {

  public final String id;
  public final String currency;
  public final String address;
  public final BigDecimal amount;
  public final String txid;
  public final long timestamp;
  public final String status;

  public GateioDeposit(
      @JsonProperty("id") String id,
      @JsonProperty("currency") String currency,
      @JsonProperty("address") String address,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("txid") String txid,
      @JsonProperty("timestamp") long timestamp,
      @JsonProperty("status") String status) {
    this.id = id;
    this.currency = currency;
    this.address = address;
    this.amount = amount;
    this.txid = txid;
    this.timestamp = timestamp;
    this.status = status;
  }

  public Date getTimestamp() {
    return new Date(timestamp * 1000);
  }

  @Override
  public String toString() {
    return "GateioDeposit [id="
        + id
        + ", currency="
        + currency
        + ", address="
        + address
        + ", amount="
        + amount
        + ", txid="
        + txid
        + ", status="
        + status
        + ", getTimestamp()="
        + getTimestamp()
        + "]";
  }
}
