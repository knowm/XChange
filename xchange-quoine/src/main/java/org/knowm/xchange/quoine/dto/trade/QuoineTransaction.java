package org.knowm.xchange.quoine.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class QuoineTransaction {
  public final String id;
  public final Long createdAt;
  public final BigDecimal gross_amount;
  public final BigDecimal net_amount;
  public final BigDecimal exchange_fee;
  public final BigDecimal network_fee;
  public final String transaction_type;
  public final String from_account_id;
  public final String to_account_id;
  public final String from_role;
  public final String to_role;
  public final String state;
  public final String transaction_hash;
  public final String execution;
  public final String loan;
  public final String notes;

  public QuoineTransaction(
      @JsonProperty("id") String id,
      @JsonProperty("created_at") Long createdAt,
      @JsonProperty("gross_amount") BigDecimal gross_amount,
      @JsonProperty("net_amount") BigDecimal net_amount,
      @JsonProperty("exchange_fee") BigDecimal exchange_fee,
      @JsonProperty("network_fee") BigDecimal network_fee,
      @JsonProperty("transaction_type") String transaction_type,
      @JsonProperty("from_account_id") String from_account_id,
      @JsonProperty("to_account_id") String to_account_id,
      @JsonProperty("from_role") String from_role,
      @JsonProperty("to_role") String to_role,
      @JsonProperty("state") String state,
      @JsonProperty("transaction_hash") String transaction_hash,
      @JsonProperty("execution") String execution,
      @JsonProperty("loan") String loan,
      @JsonProperty("notes") String notes) {
    this.id = id;
    this.createdAt = createdAt;
    this.gross_amount = gross_amount;
    this.net_amount = net_amount;
    this.exchange_fee = exchange_fee;
    this.network_fee = network_fee;
    this.transaction_type = transaction_type;
    this.from_account_id = from_account_id;
    this.to_account_id = to_account_id;
    this.from_role = from_role;
    this.to_role = to_role;
    this.state = state;
    this.transaction_hash = transaction_hash;
    this.execution = execution;
    this.loan = loan;
    this.notes = notes;
  }

  @Override
  public String toString() {
    return "QuoineTransaction{"
        + "id='"
        + id
        + '\''
        + ", createdAt="
        + createdAt
        + ", gross_amount="
        + gross_amount
        + ", net_amount="
        + net_amount
        + ", exchange_fee="
        + exchange_fee
        + ", network_fee="
        + network_fee
        + ", transaction_type='"
        + transaction_type
        + '\''
        + ", from_account_id='"
        + from_account_id
        + '\''
        + ", to_account_id='"
        + to_account_id
        + '\''
        + ", from_role='"
        + from_role
        + '\''
        + ", to_role='"
        + to_role
        + '\''
        + ", state='"
        + state
        + '\''
        + ", transaction_hash='"
        + transaction_hash
        + '\''
        + ", execution='"
        + execution
        + '\''
        + ", loan='"
        + loan
        + '\''
        + '}';
  }
}
