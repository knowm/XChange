package org.knowm.xchange.coinsph.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a withdrawal record from Coins.ph API Based on GET /openapi/wallet/v1/withdraw/history
 * endpoint
 */
@Getter
@ToString
public class CoinsphWithdrawalRecord {

  private final String id;
  private final BigDecimal amount;
  private final BigDecimal transactionFee;
  private final String coin;
  private final int status; // 0: pending, 1: success, etc.
  private final String address;
  private final String addressTag;
  private final String txId;
  private final long applyTime;
  private final String network;
  private final String withdrawOrderId;
  private final String info;
  private final int confirmNo;

  public CoinsphWithdrawalRecord(
      @JsonProperty("id") String id,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("transactionFee") BigDecimal transactionFee,
      @JsonProperty("coin") String coin,
      @JsonProperty("status") int status,
      @JsonProperty("address") String address,
      @JsonProperty("addressTag") String addressTag,
      @JsonProperty("txId") String txId,
      @JsonProperty("applyTime") long applyTime,
      @JsonProperty("network") String network,
      @JsonProperty("withdrawOrderId") String withdrawOrderId,
      @JsonProperty("info") String info,
      @JsonProperty("confirmNo") int confirmNo) {
    this.id = id;
    this.amount = amount;
    this.transactionFee = transactionFee;
    this.coin = coin;
    this.status = status;
    this.address = address;
    this.addressTag = addressTag;
    this.txId = txId;
    this.applyTime = applyTime;
    this.network = network;
    this.withdrawOrderId = withdrawOrderId;
    this.info = info;
    this.confirmNo = confirmNo;
  }
}
