package org.knowm.xchange.poloniex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class PoloniexDeposit {

  private final String currency;
  private final String address;
  private final BigDecimal amount;
  private final int confirmations;
  private final String txid;
  private final Date timestamp;
  private final String status;

  public PoloniexDeposit(
      @JsonProperty("currency") String currency,
      @JsonProperty("address") String address,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("confirmations") int confirmations,
      @JsonProperty("txid") String txid,
      @JsonProperty("timestamp") long timestamp,
      @JsonProperty("status") String status) {
    super();
    this.currency = currency;
    this.address = address;
    this.amount = amount;
    this.confirmations = confirmations;
    this.txid = txid;
    this.timestamp = new Date(timestamp * 1000);
    this.status = status;
  }

  public String getCurrency() {
    return currency;
  }

  public String getAddress() {
    return address;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public int getConfirmations() {
    return confirmations;
  }

  public String getTxid() {
    return txid;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public String getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return "PoloniexDeposit [currency="
        + currency
        + ", address="
        + address
        + ", amount="
        + amount
        + ", confirmations="
        + confirmations
        + ", txid="
        + txid
        + ", timestamp="
        + timestamp
        + ", status="
        + status
        + "]";
  }
}
