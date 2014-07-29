package com.xeiam.xchange.vaultofsatoshi.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>
 * Data object representing a Trade from VaultOfSatoshi
 * </p>
 */

public final class VaultOfSatoshiTrade {

  private final long transaction_date;
  private final long journalId;
  private final long transactionId;
  private final int inverseTrade;
  private final VosCurrency units_traded;
  private final VosCurrency price;
  private final VosCurrency total;

  public VaultOfSatoshiTrade(@JsonProperty("transaction_date") long transaction_date, @JsonProperty("journal_id") long journalId, @JsonProperty("transaction_id") long transactionId,
      @JsonProperty("inverse_trade") int inverseTrade, @JsonProperty("units_traded") VosCurrency units_traded, @JsonProperty("price") VosCurrency price, @JsonProperty("total") VosCurrency total) {

    this.transaction_date = transaction_date;
    this.journalId = journalId;
    this.transactionId = transactionId;
    this.inverseTrade = inverseTrade;
    this.units_traded = units_traded;
    this.total = total;
    this.price = price;
  }

  public VosCurrency getPrice() {

    return price;
  }

  public VosCurrency getTotal() {

    return total;
  }

  public VosCurrency getUnitsTraded() {

    return units_traded;
  }

  public long getTimestamp() {

    return transaction_date;
  }

  public long getJournalId() {

    return journalId;
  }

  public long getTransactionId() {

    return transactionId;
  }

  public int getInverseTrade() {

    return inverseTrade;
  }

  @Override
  public String toString() {

    return "VaultOfSatoshiTrade [transaction_date=" + transaction_date + ", journalId=" + journalId + ", transactionId=" + transactionId + ", inverseTrade=" + inverseTrade + ", units_traded="
        + units_traded + ", price=" + price + ", total=" + total + "]";
  }

}
