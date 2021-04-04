package org.knowm.xchange.coinbase.v2.dto.account.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import lombok.Getter;
import org.knowm.xchange.coinbase.v2.dto.CoinbaseAmount;

@Getter
public class CoinbaseBuySell {
  private final String id;
  private final String status;
  private final CoinbaseBuySellResourceField paymentMethod;
  private final CoinbaseBuySellResourceField transaction;
  private final CoinbaseAmount amount;
  private final CoinbaseAmount total;
  private final CoinbaseAmount subTotal;
  private final String createdAt;
  private final String updatedAt;
  private final String resource;
  private final String resourcePath;
  private final boolean committed;
  private final boolean instant;
  private final CoinbaseAmount fee;
  private final String payoutAt;

  public CoinbaseBuySell(
      @JsonProperty("id") String id,
      @JsonProperty("status") String status,
      @JsonProperty("payment_method") CoinbaseBuySellResourceField paymentMethod,
      @JsonProperty("transaction") CoinbaseBuySellResourceField transaction,
      @JsonProperty("amount") CoinbaseAmount amount,
      @JsonProperty("total") CoinbaseAmount total,
      @JsonProperty("subtotal") CoinbaseAmount subTotal,
      @JsonProperty("created_at") String createdAt,
      @JsonProperty("updated_at") String updatedAt,
      @JsonProperty("resource") String resource,
      @JsonProperty("resource_path") String resourcePath,
      @JsonProperty("committed") boolean committed,
      @JsonProperty("instant") boolean instant,
      @JsonProperty("fee") CoinbaseAmount fee,
      @JsonProperty("payout_at") String payoutAt) {
    this.id = id;
    this.status = status;
    this.paymentMethod = paymentMethod;
    this.transaction = transaction;
    this.amount = amount;
    this.total = total;
    this.subTotal = subTotal;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.resource = resource;
    this.resourcePath = resourcePath;
    this.committed = committed;
    this.instant = instant;
    this.fee = fee;
    this.payoutAt = payoutAt;
  }

  public ZonedDateTime getCreatedAt() {
    return ZonedDateTime.parse(createdAt);
  }

  public ZonedDateTime getUpdatedAt() {
    return ZonedDateTime.parse(updatedAt);
  }

  public ZonedDateTime getPayoutAt() {
    return ZonedDateTime.parse(payoutAt);
  }

  @Override
  public String toString() {
    return "{"
        + "\"id\":"
        + '\"'
        + id
        + '\"'
        + ",\"status\":"
        + '\"'
        + status
        + '\"'
        + ",\"paymentMethod\":"
        + '\"'
        + paymentMethod
        + '\"'
        + ",\"transaction\":"
        + '\"'
        + transaction
        + '\"'
        + ",\"amount\":"
        + '\"'
        + amount
        + '\"'
        + ",\"total\":"
        + '\"'
        + total
        + '\"'
        + ",\"subTotal\":"
        + '\"'
        + subTotal
        + '\"'
        + ",\"createdAt\":"
        + '\"'
        + createdAt
        + '\"'
        + ",\"updatedAt\":"
        + '\"'
        + updatedAt
        + '\"'
        + ",\"resource\":"
        + '\"'
        + resource
        + '\"'
        + ",\"resourcePath\":"
        + '\"'
        + resourcePath
        + '\"'
        + ",\"committed\":"
        + '\"'
        + committed
        + '\"'
        + ",\"instant\":"
        + instant
        + ",\"fee\":"
        + fee
        + ",\"payoutAt\":"
        + payoutAt
        + '}';
  }
}
