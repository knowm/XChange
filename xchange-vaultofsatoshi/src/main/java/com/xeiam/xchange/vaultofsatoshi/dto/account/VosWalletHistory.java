package com.xeiam.xchange.vaultofsatoshi.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosCurrency;

/**
 * @author Michael Lagacé
 */
public final class VosWalletHistory {

  private int transfer_id;
  private long transfer_date;
  private String type;
  private VosCurrency units;
  private String transaction_id;
  private String currency;

  public VosWalletHistory(@JsonProperty("transfer_id") int transfer_id, @JsonProperty("transfer_date") long transfer_date, @JsonProperty("type") String type, @JsonProperty("units") VosCurrency units,
      @JsonProperty("transaction_id") String transaction_id, @JsonProperty("currency") String currency) {

    this.transfer_id = transfer_id;
    this.transfer_date = transfer_date;
    this.type = type;
    this.units = units;
    this.transaction_id = transaction_id;
    this.currency = currency;
  }

  public int getTransfer_id() {

    return transfer_id;
  }

  public long getTransfer_date() {

    return transfer_date;
  }

  public String getType() {

    return type;
  }

  public VosCurrency getUnits() {

    return units;
  }

  public String getTransaction_id() {

    return transaction_id;
  }

  public String getCurrency() {

    return currency;
  }

  @Override
  public String toString() {

    return "VosWalletHistory [transfer_id=" + transfer_id + ", transfer_date=" + transfer_date + ", type=" + type + ", units=" + units + ", transaction_id=" + transaction_id + ", currency="
        + currency + "]";
  }

}
