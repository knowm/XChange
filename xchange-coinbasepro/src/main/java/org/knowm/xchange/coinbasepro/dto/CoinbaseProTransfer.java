package org.knowm.xchange.coinbasepro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.dto.account.FundingRecord.Type;

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

@ToString
@Getter
public class CoinbaseProTransfer {

  private final String id;
  private final Type type;
  private final Date createdAt;
  private final Date completedAt;
  private final Date canceledAt;
  private final Date processedAt;
  private final String accountId;
  private final String userId;
  private final String userNonce;
  private final BigDecimal amount;
  private final String idem;
  private final String currency;
  private final Detail details;

  public CoinbaseProTransfer(
      @JsonProperty("id") String id,
      @JsonProperty("type") String type,
      @JsonProperty("created_at") String createdAt,
      @JsonProperty("completed_at") String completedAt,
      @JsonProperty("canceled_at") String canceledAt,
      @JsonProperty("processed_at") String processedAt,
      @JsonProperty("account_id") String accountId,
      @JsonProperty("user_id") String userId,
      @JsonProperty("user_nonce") String userNonce,
      @JsonProperty("amount") String amount,
      @JsonProperty("idem") String idem,
      @JsonProperty("currency") String currency,
      @JsonProperty("details") Detail details) {
    this.id = id;
    this.type = Type.valueOf(type.toUpperCase());
    this.createdAt = parse(createdAt);
    this.completedAt = parse(completedAt);
    this.canceledAt = parse(canceledAt);
    this.processedAt = parse(processedAt);
    this.accountId = accountId;
    this.userId = userId;
    this.userNonce = userNonce;
    this.amount = (amount == null) ? BigDecimal.ZERO : new BigDecimal(amount);
    this.idem = idem;
    this.currency = currency;
    this.details = details;
  }

  @ToString
  @Getter
  public static class Detail {
    private final String cryptoAddress;
    private final String coinbaseAccountId;
    private final String cryptoTransactionId;
    private final String coinbaseTransactionId;
    private final String cryptoTransactionHash;
    private final String sentToAddress;
    private final String coinbaseWithdrawalId;
    private final String destinationTag;
    private final String destinationTagName;

    public Detail(
        @JsonProperty("crypto_address") String cryptoAddress,
        @JsonProperty("coinbase_account_id") String coinbaseAccountId,
        @JsonProperty("crypto_transaction_id") String cryptoTransactionId,
        @JsonProperty("coinbase_transaction_id") String coinbaseTransactionId,
        @JsonProperty("crypto_transaction_hash") String cryptoTransactionHash,
        @JsonProperty("sent_to_address") String sentToAddress,
        @JsonProperty("coinbase_withdrawal_id") String coinbaseWithdrawalId,
        @JsonProperty("destination_tag") String destinationTag,
        @JsonProperty("destination_tag_name") String destinationTagName) {
      this.cryptoAddress = cryptoAddress;
      this.coinbaseAccountId = coinbaseAccountId;
      this.cryptoTransactionId = cryptoTransactionId;
      this.coinbaseTransactionId = coinbaseTransactionId;
      this.cryptoTransactionHash = cryptoTransactionHash;
      this.sentToAddress = sentToAddress;
      this.coinbaseWithdrawalId = coinbaseWithdrawalId;
      this.destinationTag = destinationTag;
      this.destinationTagName = destinationTagName;
    }
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
}
