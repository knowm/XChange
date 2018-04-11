package org.knowm.xchange.luno.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

public class LunoWithdrawals {

  private final Withdrawal[] withdrawals;

  public LunoWithdrawals(
      @JsonProperty(value = "withdrawals", required = true) Withdrawal[] withdrawals) {
    this.withdrawals = withdrawals != null ? withdrawals : new Withdrawal[0];
  }

  public Withdrawal[] getWithdrawals() {
    Withdrawal[] copy = new Withdrawal[withdrawals.length];
    System.arraycopy(withdrawals, 0, copy, 0, withdrawals.length);
    return copy;
  }

  @Override
  public String toString() {
    return "LunoWithdrawals [withdrawals("
        + withdrawals.length
        + ")="
        + Arrays.toString(withdrawals)
        + "]";
  }

  public static enum Status {
    PENDING,
    COMPLETED,
    CANCELLED,
    UNKNOWN;

    @JsonCreator
    public static Status create(String s) {
      try {
        return Status.valueOf(s);
      } catch (Exception e) {
        return UNKNOWN;
      }
    }
  }

  public static class Withdrawal {
    public final String id;
    public final Status status;
    public final long createdAt;
    public final String type;
    public final String currency;
    public final BigDecimal amount;
    public final BigDecimal fee;

    public Withdrawal(
        @JsonProperty(value = "id", required = true) String id,
        @JsonProperty(value = "status", required = false) Status status,
        @JsonProperty(value = "created_at", required = false) long createdAt,
        @JsonProperty(value = "type", required = false) String type,
        @JsonProperty(value = "currency", required = false) String currency,
        @JsonProperty(value = "amount", required = false) BigDecimal amount,
        @JsonProperty(value = "fee", required = false) BigDecimal fee) {
      this.id = id;
      this.status = status;
      this.createdAt = createdAt;
      this.type = type;
      this.currency = currency;
      this.amount = amount;
      this.fee = fee;
    }

    public Date getCreatedAt() {
      return new Date(createdAt);
    }

    @Override
    public String toString() {
      return "Withdrawal [id="
          + id
          + ", status="
          + status
          + ", createdAt="
          + getCreatedAt()
          + ", type="
          + type
          + ", currency="
          + currency
          + ", amount="
          + amount
          + ", fee="
          + fee
          + "]";
    }
  }
}
