package org.knowm.xchange.gemini.v1.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeminiOrderStatusRequest {

  @JsonProperty("request")
  protected String request;

  @JsonProperty("nonce")
  protected String nonce;

  @JsonProperty("order_id")
  @JsonRawValue
  private long orderId;

  @JsonProperty("client_order_id")
  private String clientOrderId; // Optional, but cannot be used in combination with orderId

  @JsonProperty("include_trades")
  private boolean includeTrades; // Optional, defaults to false

  @JsonProperty("account")
  private String account; // Optional

  /**
   * Constructor for requests without optional fields
   *
   * @param nonce
   * @param orderId
   */
  public GeminiOrderStatusRequest(String nonce, long orderId) {
    this.request = "/v1/order/status";
    this.orderId = orderId;
    this.nonce = nonce;
  }

  /**
   * Constructor for requests with optional fields, order lookup by exchange order id
   *
   * @param nonce
   * @param orderId
   * @param includeTrades
   * @param account
   */
  public GeminiOrderStatusRequest(
      String nonce, long orderId, boolean includeTrades, String account) {
    this.request = "/v1/order/status";
    this.orderId = orderId;
    this.nonce = nonce;
    this.includeTrades = includeTrades;
    this.account = account;
  }

  /**
   * Constructor for requests with optional fields, order lookup by client order id
   *
   * @param nonce
   * @param clientOrderId
   * @param includeTrades
   * @param account
   */
  public GeminiOrderStatusRequest(
      String nonce, String clientOrderId, boolean includeTrades, String account) {
    this.request = "/v1/order/status";
    this.clientOrderId = clientOrderId;
    this.nonce = nonce;
    this.includeTrades = includeTrades;
    this.account = account;
  }

  public String getOrderId() {
    return String.valueOf(orderId);
  }
}
