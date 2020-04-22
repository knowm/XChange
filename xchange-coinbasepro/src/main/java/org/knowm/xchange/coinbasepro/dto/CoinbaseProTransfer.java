package org.knowm.xchange.coinbasepro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.knowm.xchange.dto.account.FundingRecord;

/*
examples:

{
    "id": "18dc7a83-8c63-43f9-81f1-1ddb64fd6fc5",
    "type": "deposit",
    "created_at": "2018-02-14 01:01:53.84988+00",
    "completed_at": "2018-02-14 01:25:05.431164+00",
    "canceled_at": null,
    "processed_at": "2018-02-14 01:25:05.431164+00",
    "user_nonce": null,
    "amount": "6.22443421",
    "details": {
      "crypto_address": "LUmubWJR7stibtBuWqQ4FDqBGC2kaqqoQm",
      "coinbase_account_id": "f715c8ab-de34-58d2-93b3-36d41e96c9e3",
      "crypto_transaction_id": "5a838a8079ed9700015f2f8b",
      "coinbase_transaction_id": "5a838ff19814b701ef213e1f",
      "crypto_transaction_hash": "62f8b8b659cd27866a63e5f4effae36663657c8d49db3b45e5482d62fddd3743"
    }
  }

{
    "id": "6e91a83d-445f-4848-8cd5-7b12830f3724",
    "type": "withdraw",
    "created_at": "2018-03-29 04:38:21.560296+00",
    "completed_at": "2018-03-29 04:38:21.998732+00",
    "canceled_at": null,
    "processed_at": "2018-03-29 04:38:26.19891+00",
    "user_nonce": null,
    "amount": "0.82793994",
    "details": {
      "sent_to_address": "3P6jRuufEenxJqFzY6sVasaYKNzrTJtTSX",
      "coinbase_account_id": "c6afbd34-4bd0-501e-8616-4862c193cd84",
      "coinbase_withdrawal_id": "0c2f4b4c-584d-5a6c-a323-e154a4d02b88",
      "coinbase_transaction_id": "5abc6dbd621efe019cf07a48",
      "crypto_transaction_hash": "3bacd5ab5ee8f1177a076e445b20176ed5e3f3ce71d52a98c1daeef959ed8c81"
    }
  }
 */

public class CoinbaseProTransfer {

  public static class Detail {
    public final String cryptoAddress;
    public final String coinbaseAccountId;
    public final String cryptoTransactionId;
    public final String coinbaseTransactionId;
    public final String cryptoTransactionHash;
    public final String sentToAddress;
    public final String coinbaseWithdrawalId;
    private final String destinationTag;

    public Detail(
        @JsonProperty("crypto_address") String cryptoAddress,
        @JsonProperty("coinbase_account_id") String coinbaseAccountId,
        @JsonProperty("crypto_transaction_id") String cryptoTransactionId,
        @JsonProperty("coinbase_transaction_id") String coinbaseTransactionId,
        @JsonProperty("crypto_transaction_hash") String cryptoTransactionHash,
        @JsonProperty("sent_to_address") String sentToAddress,
        @JsonProperty("coinbase_withdrawal_id") String coinbaseWithdrawalId,
        @JsonProperty("destination_tag") String destinationTag) {
      this.cryptoAddress = cryptoAddress;
      this.coinbaseAccountId = coinbaseAccountId;
      this.cryptoTransactionId = cryptoTransactionId;
      this.coinbaseTransactionId = coinbaseTransactionId;
      this.cryptoTransactionHash = cryptoTransactionHash;
      this.sentToAddress = sentToAddress;
      this.coinbaseWithdrawalId = coinbaseWithdrawalId;
      this.destinationTag = destinationTag;
    }

    public String getCryptoAddress() {
      return cryptoAddress;
    }

    public String getCoinbaseAccountId() {
      return coinbaseAccountId;
    }

    public String getCryptoTransactionId() {
      return cryptoTransactionId;
    }

    public String getCoinbaseTransactionId() {
      return coinbaseTransactionId;
    }

    public String getCryptoTransactionHash() {
      return cryptoTransactionHash;
    }

    public String getSentToAddress() {
      return sentToAddress;
    }

    public String getCoinbaseWithdrawalId() {
      return coinbaseWithdrawalId;
    }

    public String getDestinationTag() {
      return destinationTag;
    }

    @Override
    public String toString() {
      return "Detail{"
          + "cryptoAddress='"
          + cryptoAddress
          + '\''
          + "destinationTag='"
          + destinationTag
          + '\''
          + ", coinbaseAccountId='"
          + coinbaseAccountId
          + '\''
          + ", cryptoTransactionId='"
          + cryptoTransactionId
          + '\''
          + ", coinbaseTransactionId='"
          + coinbaseTransactionId
          + '\''
          + ", cryptoTransactionHash='"
          + cryptoTransactionHash
          + '\''
          + ", sentToAddress='"
          + sentToAddress
          + '\''
          + ", coinbaseWithdrawalId='"
          + coinbaseWithdrawalId
          + '\''
          + '}';
    }
  }

  public final String id;
  public final String type;
  public final String createdAt;
  public final String canceledAt;
  public final String processedAt;
  public final String amount;
  public final Detail details;

  public CoinbaseProTransfer(
      @JsonProperty("id") String id,
      @JsonProperty("type") String type,
      @JsonProperty("created_at") String createdAt,
      @JsonProperty("canceled_at") String canceledAt,
      @JsonProperty("processed_at") String processedAt,
      @JsonProperty("amount") String amount,
      @JsonProperty("details") Detail details) {
    this.id = id;
    this.type = type;
    this.createdAt = createdAt;
    this.canceledAt = canceledAt;
    this.processedAt = processedAt;
    this.amount = amount;
    this.details = details;
  }

  public String getId() {
    return id;
  }

  public String getType() {
    return type;
  }

  public FundingRecord.Type type() {
    return type.equalsIgnoreCase("withdraw")
        ? FundingRecord.Type.WITHDRAWAL
        : FundingRecord.Type.DEPOSIT;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public Date createdAt() {
    return parse(createdAt);
  }

  public String getCanceledAt() {
    return canceledAt;
  }

  public Date canceledAt() {
    return parse(canceledAt);
  }

  public String getProcessedAt() {
    return processedAt;
  }

  public Date processedAt() {
    return parse(processedAt);
  }

  public String getAmount() {
    return amount;
  }

  public BigDecimal amount() {
    return new BigDecimal(amount);
  }

  public Detail getDetails() {
    return details;
  }

  private static Date parse(String time) {
    try {
      return time == null
          ? null
          : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSSZ").parse(time + "00");
    } catch (ParseException e) {
      throw new IllegalStateException("Cannot parse '" + time + "'", e);
    }
  }

  @Override
  public String toString() {
    return "CoinbaseProTransfer{"
        + "id='"
        + id
        + '\''
        + ", type='"
        + type
        + '\''
        + ", createdAt='"
        + createdAt
        + '\''
        + ", canceledAt='"
        + canceledAt
        + '\''
        + ", processedAt='"
        + processedAt
        + '\''
        + ", amount='"
        + amount
        + '\''
        + ", details="
        + details
        + '}';
  }
}
