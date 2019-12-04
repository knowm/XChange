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

  public CoinmateTransferHistoryEntry(
      @JsonProperty("id") long id,
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("transferType") String transferType,
      @JsonProperty("timestamp") long timestamp,
      @JsonProperty("transferStatus") String transferStatus,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("amountCurrency") String amountCurrency,
      @JsonProperty("walletType") String walletType) {

    this.fee = fee;
    this.transferType = transferType;
    this.timestamp = timestamp;
    this.id = id;
    this.transferStatus = transferStatus;
    this.amount = amount;
    this.amountCurrency = amountCurrency;
    this.walletType = walletType;
  }
}
