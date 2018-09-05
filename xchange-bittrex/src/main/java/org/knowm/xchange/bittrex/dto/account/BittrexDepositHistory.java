package org.knowm.xchange.bittrex.dto.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;

/** @author npinot */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"Id", "Amount", "Currency", "LastUpdated", "TxId", "CryptoAddress"})
public class BittrexDepositHistory {
  @JsonProperty("Id")
  private Long id;

  @JsonProperty("Amount")
  private BigDecimal amount;

  @JsonProperty("Currency")
  private String currency;

  @JsonProperty("Confirmations")
  private Integer confirmations;

  @JsonProperty("LastUpdated")
  private Date lastUpdated;

  @JsonProperty("TxId")
  private String txId;

  @JsonProperty("CryptoAddress")
  private String cryptoAddress;

  @JsonProperty("Id")
  public Long getId() {

    return id;
  }

  @JsonProperty("Id")
  public void setId(Long id) {

    this.id = id;
  }

  @JsonProperty("Amount")
  public BigDecimal getAmount() {

    return amount;
  }

  @JsonProperty("Amount")
  public void setAmount(BigDecimal amount) {

    this.amount = amount;
  }

  @JsonProperty("Currency")
  public String getCurrency() {

    return currency;
  }

  @JsonProperty("Currency")
  public void setCurrency(String currency) {

    this.currency = currency;
  }

  @JsonProperty("Confirmations")
  public Integer getConfirmations() {

    return confirmations;
  }

  @JsonProperty("Confirmations")
  public void setConfirmations(Integer confirmations) {

    this.confirmations = confirmations;
  }

  @JsonProperty("LastUpdated")
  public Date getLastUpdated() {

    return lastUpdated;
  }

  @JsonProperty("LastUpdated")
  public void setLastUpdated(Date lastUpdated) {

    this.lastUpdated = lastUpdated;
  }

  @JsonProperty("TxId")
  public String getTxId() {

    return txId;
  }

  @JsonProperty("TxId")
  public void setTxId(String txId) {

    this.txId = txId;
  }

  @JsonProperty("CryptoAddress")
  public String getCryptoAddress() {

    return cryptoAddress;
  }

  @JsonProperty("CryptoAddress")
  public void setCryptoAddress(String cryptoAddress) {

    this.cryptoAddress = cryptoAddress;
  }
}
