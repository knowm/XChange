package org.knowm.xchange.coinmate.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class CoinmateTransferHistoryEntry {
  private final long id;
  private final BigDecimal fee;
  private final String transferType;
  private final long timestamp;
  private final String transferStatus;
  private final BigDecimal amount;
  private final String amountCurrency;
  private final String walletType;
  private final String destination;
  private final String destinationTag;

  public CoinmateTransferHistoryEntry(
      @JsonProperty("transactionId") long id,
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("transferType") String transferType,
      @JsonProperty("timestamp") long timestamp,
      @JsonProperty("transferStatus") String transferStatus,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("amountCurrency") String amountCurrency,
      @JsonProperty("walletType") String walletType,
      @JsonProperty("destination") String destination,
      @JsonProperty("destinationTag") String destinationTag
  ) {

    this.fee = fee;
    this.transferType = transferType;
    this.timestamp = timestamp;
    this.id = id;
    this.transferStatus = transferStatus;
    this.amount = amount;
    this.amountCurrency = amountCurrency;
    this.walletType = walletType;
    this.destination = destination;
    this.destinationTag = destinationTag;
  }

  public long getId() {
    return id;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public String getTransferType() {
    return transferType;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public String getTransferStatus() {
    return transferStatus;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getAmountCurrency() {
    return amountCurrency;
  }

  public String getWalletType() {
    return walletType;
  }

  public String getDestination() {
    return destination;
  }

  public String getDestinationTag() {
    return destinationTag;
  }
}
