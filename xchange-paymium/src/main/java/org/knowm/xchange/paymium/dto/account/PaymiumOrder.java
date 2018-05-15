package org.knowm.xchange.paymium.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

public class PaymiumOrder {

  @JsonProperty("AccountOperations")
  private List<PaymiumAccountOperations> accountOperations;

  @JsonProperty("Amount")
  private BigDecimal amount;

  @JsonProperty("BitcoinAddress")
  private String bitcoinAddress;

  @JsonProperty("BtcFee")
  private BigDecimal btcFee;

  @JsonProperty("Comment")
  private String comment;

  @JsonProperty("CreatedAt")
  private String createdAt;

  @JsonProperty("Currency")
  private String currency;

  @JsonProperty("CurrencyAmount")
  private BigDecimal currencyAmount;

  @JsonProperty("CurrencyFee")
  private BigDecimal currencyFee;

  @JsonProperty("Direction")
  private String direction;

  @JsonProperty("Price")
  private BigDecimal price;

  @JsonProperty("State")
  private String state;

  @JsonProperty("TradedBtc")
  private BigDecimal tradedBtc;

  @JsonProperty("TradedCurrency")
  private BigDecimal tradedCurrency;

  @JsonProperty("Txid")
  private String txid;

  @JsonProperty("Type")
  private String type;

  @JsonProperty("UpdatedAt")
  private String updatedAt;

  @JsonProperty("Uuid")
  private String uuid;

  @Override
  public String toString() {
    return "PaymiumOrder [accountOperations="
        + accountOperations
        + ", bitcoinAddress= "
        + bitcoinAddress
        + ", btcFee="
        + btcFee
        + ", comment= "
        + comment
        + ", createdAt="
        + createdAt
        + ", currency= "
        + currency
        + ", currencyAmount="
        + currencyAmount
        + ", currencyFee= "
        + currencyFee
        + ", direction="
        + direction
        + ", price= "
        + price
        + ", state="
        + state
        + ", tradedBtc= "
        + tradedBtc
        + ", tradedCurrency="
        + tradedCurrency
        + ", txid= "
        + txid
        + ", type="
        + type
        + ", updatedAt= "
        + updatedAt
        + ", uuid="
        + uuid
        + "]";
  }
}
