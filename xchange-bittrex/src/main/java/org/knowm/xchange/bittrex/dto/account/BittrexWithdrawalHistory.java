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
@JsonPropertyOrder({
  "PaymentUuid",
  "Currency",
  "Amount",
  "Opened",
  "Authorized",
  "PendingPayment",
  "TxCost",
  "TxId",
  "Canceled",
  "InvalidAddress"
})
public class BittrexWithdrawalHistory {
  @JsonProperty("PaymentUuid")
  private String paymentUuid;

  @JsonProperty("Currency")
  private String currency;

  @JsonProperty("Amount")
  private BigDecimal amount;

  @JsonProperty("Address")
  private String address;

  @JsonProperty("Opened")
  private Date opened;

  @JsonProperty("Authorized")
  private Boolean authorized;

  @JsonProperty("PendingPayment")
  private Boolean pendingPayment;

  @JsonProperty("TxCost")
  private BigDecimal txCost;

  @JsonProperty("TxId")
  private String txId;

  @JsonProperty("Canceled")
  private Boolean canceled;

  @JsonProperty("InvalidAddress")
  private Boolean invalidAddress;

  @JsonProperty("PaymentUuid")
  public String getPaymentUuid() {
    return paymentUuid;
  }

  @JsonProperty("PaymentUuid")
  public void setPaymentUuid(String paymentUuid) {
    this.paymentUuid = paymentUuid;
  }

  @JsonProperty("Currency")
  public String getCurrency() {
    return currency;
  }

  @JsonProperty("Currency")
  public void setCurrency(String currency) {
    this.currency = currency;
  }

  @JsonProperty("Amount")
  public BigDecimal getAmount() {
    return amount;
  }

  @JsonProperty("Amount")
  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  @JsonProperty("Address")
  public String getAddress() {
    return address;
  }

  @JsonProperty("Address")
  public void setAddress(String address) {
    this.address = address;
  }

  @JsonProperty("Opened")
  public Date getOpened() {
    return opened;
  }

  @JsonProperty("Opened")
  public void setOpened(Date opened) {
    this.opened = opened;
  }

  @JsonProperty("Authorized")
  public Boolean getAuthorized() {
    return authorized;
  }

  @JsonProperty("Authorized")
  public void setAuthorized(Boolean authorized) {
    this.authorized = authorized;
  }

  @JsonProperty("PendingPayment")
  public Boolean getPendingPayment() {
    return pendingPayment;
  }

  @JsonProperty("PendingPayment")
  public void setPendingPayment(Boolean pendingPayment) {
    this.pendingPayment = pendingPayment;
  }

  @JsonProperty("TxCost")
  public BigDecimal getTxCost() {
    return txCost;
  }

  @JsonProperty("TxCost")
  public void setTxCost(BigDecimal txCost) {
    this.txCost = txCost;
  }

  @JsonProperty("TxId")
  public String getTxId() {
    return txId;
  }

  @JsonProperty("TxId")
  public void setTxId(String txId) {
    this.txId = txId;
  }

  @JsonProperty("Canceled")
  public Boolean getCanceled() {
    return canceled;
  }

  @JsonProperty("Canceled")
  public void setCanceled(Boolean canceled) {
    this.canceled = canceled;
  }

  @JsonProperty("InvalidAddress")
  public Boolean getInvalidAddress() {
    return invalidAddress;
  }

  @JsonProperty("InvalidAddress")
  public void setInvalidAddress(Boolean invalidAddress) {
    this.invalidAddress = invalidAddress;
  }
}
