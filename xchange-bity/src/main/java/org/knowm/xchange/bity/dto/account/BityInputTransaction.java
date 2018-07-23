package org.knowm.xchange.bity.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BityInputTransaction {

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("order")
    private String order;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("payment_processor_fee")
    private String paymentProcessorFee;

    @JsonProperty("reference")
    private String reference;

    @JsonProperty("resource_uri")
    private String resourceUri;

    @JsonProperty("status")
    private String status;

    @JsonProperty("amount")
    public String getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(String amount) {
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

    @JsonProperty("payment_method")
    public String getPaymentMethod() {
        return paymentMethod;
    }

    @JsonProperty("payment_method")
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @JsonProperty("payment_processor_fee")
    public String getPaymentProcessorFee() {
        return paymentProcessorFee;
    }

    @JsonProperty("payment_processor_fee")
    public void setPaymentProcessorFee(String paymentProcessorFee) {
        this.paymentProcessorFee = paymentProcessorFee;
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
