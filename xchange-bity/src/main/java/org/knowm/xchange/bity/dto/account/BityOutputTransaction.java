package org.knowm.xchange.bity.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BityOutputTransaction {

  @JsonProperty("amount")
  private BigDecimal amount;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("order")
  private String order;

  @JsonProperty("payout_method")
  private String payoutMethod;

  @JsonProperty("reference")
  private String reference;

  @JsonProperty("resource_uri")
  private String resourceUri;

  @JsonProperty("status")
  private String status;

  @JsonProperty("amount")
  public BigDecimal getAmount() {
    return amount;
  }

  @JsonProperty("amount")
  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  @JsonProperty("currency")
  public String getCurrency() {
    return currency;
  }

  @JsonProperty("currency")
  public void setCurrency(String currency) {
    this.currency = currency;
  }

  @JsonProperty("order")
  public String getOrder() {
    return order;
  }

  @JsonProperty("order")
  public void setOrder(String order) {
    this.order = order;
  }

  @JsonProperty("payout_method")
  public String getPayoutMethod() {
    return payoutMethod;
  }

  @JsonProperty("payout_method")
  public void setPayoutMethod(String payoutMethod) {
    this.payoutMethod = payoutMethod;
  }

  @JsonProperty("reference")
  public String getReference() {
    return reference;
  }

  @JsonProperty("reference")
  public void setReference(String reference) {
    this.reference = reference;
  }

  @JsonProperty("resource_uri")
  public String getResourceUri() {
    return resourceUri;
  }

  @JsonProperty("resource_uri")
  public void setResourceUri(String resourceUri) {
    this.resourceUri = resourceUri;
  }

  @JsonProperty("status")
  public String getStatus() {
    return status;
  }

  @JsonProperty("status")
  public void setStatus(String status) {
    this.status = status;
  }
}
