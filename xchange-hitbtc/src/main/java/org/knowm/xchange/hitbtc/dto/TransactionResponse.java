package org.knowm.xchange.hitbtc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class TransactionResponse {
  public final String id;
  public final String type;
  public final String status;
  public final long created;
  public final long finished;
  public final BigDecimal amountFrom;
  public final String currencyCodeFrom;
  public final BigDecimal amountTo;
  public final String currencyCodeTo;
  public final String destinationData;
  public final BigDecimal commissionPercent;
  public final String bitcoinAddress;
  public final String bitcoinReturnAddress;
  public final String externalData;

  public TransactionResponse(@JsonProperty("id") String id, @JsonProperty("type") String type, @JsonProperty("status") String status,
                             @JsonProperty("created") long created, @JsonProperty("finished") long finished,
                             @JsonProperty("amount_from") BigDecimal amountFrom, @JsonProperty("currency_code_from") String currencyCodeFrom,
                             @JsonProperty("amount_to") BigDecimal amountTo, @JsonProperty("currency_code_to") String currencyCodeTo,
                             @JsonProperty("destination_data") String destinationData, @JsonProperty("commission_percent") BigDecimal commissionPercent,
                             @JsonProperty("bitcoin_address") String bitcoinAddress, @JsonProperty("bitcoin_return_address") String bitcoinReturnAddress,
                             @JsonProperty("external_data") String externalData) {
    this.id = id;
    this.type = type;
    this.status = status;
    this.created = created;
    this.finished = finished;
    this.amountFrom = amountFrom;
    this.currencyCodeFrom = currencyCodeFrom;
    this.amountTo = amountTo;
    this.currencyCodeTo = currencyCodeTo;
    this.destinationData = destinationData;
    this.commissionPercent = commissionPercent;
    this.bitcoinAddress = bitcoinAddress;
    this.bitcoinReturnAddress = bitcoinReturnAddress;
    this.externalData = externalData;
  }

  @Override
  public String toString() {
    return "TransactionResponse{" +
        "id='" + id + '\'' +
        ", type='" + type + '\'' +
        ", status='" + status + '\'' +
        ", created=" + created +
        ", finished=" + finished +
        ", amountFrom=" + amountFrom +
        ", currencyCodeFrom='" + currencyCodeFrom + '\'' +
        ", amountTo=" + amountTo +
        ", currencyCodeTo='" + currencyCodeTo + '\'' +
        ", destinationData='" + destinationData + '\'' +
        ", commissionPercent=" + commissionPercent +
        ", bitcoinAddress='" + bitcoinAddress + '\'' +
        ", bitcoinReturnAddress='" + bitcoinReturnAddress + '\'' +
        ", externalData='" + externalData + '\'' +
        '}';
  }
}
