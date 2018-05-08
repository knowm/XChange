package org.knowm.xchange.itbit.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class ItBitFunding {
  public final String time;
  public final String currency;
  public final String transactionType;
  public final BigDecimal amount;
  public final String walletName;
  public final String status;

  // Withdrawals
  public final String bankName;
  public final String withdrawalId;
  public final String holdingPeriodCompletionDate;

  // Deposits
  public final String destinationAddress;
  public final String txnHash;

  public ItBitFunding(
      @JsonProperty("time") String time,
      @JsonProperty("currency") String currency,
      @JsonProperty("transactionType") String transactionType,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("walletName") String walletName,
      @JsonProperty("status") String status,
      @JsonProperty("bankName") String bankName,
      @JsonProperty("withdrawalId") String withdrawalId,
      @JsonProperty("holdingPeriodCompletionDate") String holdingPeriodCompletionDate,
      @JsonProperty("destinationAddress") String destinationAddress,
      @JsonProperty("txnHash") String txnHash) {
    this.time = time;
    this.currency = currency;
    this.transactionType = transactionType;
    this.amount = amount;
    this.walletName = walletName;
    this.status = status;
    this.bankName = bankName;
    this.withdrawalId = withdrawalId;
    this.holdingPeriodCompletionDate = holdingPeriodCompletionDate;
    this.destinationAddress = destinationAddress;
    this.txnHash = txnHash;
  }
}
