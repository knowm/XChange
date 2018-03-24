package org.knowm.xchange.ripple.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"secret", "order"})
public class RippleOrderEntryRequest {

  @JsonProperty("secret")
  private String secret;

  @JsonProperty("order")
  private RippleOrderEntryRequestBody order = new RippleOrderEntryRequestBody();

  public String getSecret() {
    return secret;
  }

  public void setSecret(final String value) {
    secret = value;
  }

  public RippleOrderEntryRequestBody getOrder() {
    return order;
  }

  public void setOrder(final RippleOrderEntryRequestBody value) {
    order = value;
  }

  @Override
  public String toString() {
    return order.toString(); // do not log secret
  }
}
