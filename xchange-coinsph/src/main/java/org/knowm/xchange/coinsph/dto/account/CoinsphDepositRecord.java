package org.knowm.xchange.coinsph.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a deposit record from Coins.ph API Based on GET /openapi/wallet/v1/deposit/history
 * endpoint
 */
@Getter
@ToString
public class CoinsphDepositRecord {

  private final String id;
  private final BigDecimal amount;
  private final String coin;
  private final String network;
  private final int status; // 0: pending, 1: success
  private final String address;
  private final String addressTag;
  private final String txId;
  private final long insertTime;
  private final int confirmNo;

  public CoinsphDepositRecord(
      @JsonProperty("id") String id,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("coin") String coin,
      @JsonProperty("network") String network,
      @JsonProperty("status") int status,
      @JsonProperty("address") String address,
      @JsonProperty("addressTag") String addressTag,
      @JsonProperty("txId") String txId,
      @JsonProperty("insertTime") long insertTime,
      @JsonProperty("confirmNo") int confirmNo) {
    this.id = id;
    this.amount = amount;
    this.coin = coin;
    this.network = network;
    this.status = status;
    this.address = address;
    this.addressTag = addressTag;
    this.txId = txId;
    this.insertTime = insertTime;
    this.confirmNo = confirmNo;
  }
}
