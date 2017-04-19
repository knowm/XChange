package org.knowm.xchange.bitfinex.v1.dto.account;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Bitfinex withdrawal response mapping class
 *
 * @author Ondrej Novotny <ondrej.novotny@onlab.cz>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"status", "message", "withdrawal_id"})

public class BitfinexWithdrawalResponse {

  @JsonProperty("status")
  private String status;
  @JsonProperty("message")
  private String message;
  @JsonProperty("withdrawal_id")
  private String withdrawalId;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   * @return The status
   */
  @JsonProperty("status")
  public String getStatus() {
    return status;
  }

  /**
   * @param status The status
   */
  @JsonProperty("status")
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * @return The message
   */
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  /**
   * @param message The message
   */
  @JsonProperty("message")
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * @return The withdrawalId
   */
  @JsonProperty("withdrawal_id")
  public String getWithdrawalId() {
    return withdrawalId;
  }

  /**
   * @param withdrawalId The withdrawal_id
   */
  @JsonProperty("withdrawal_id")
  public void setWithdrawalId(String withdrawalId) {
    this.withdrawalId = withdrawalId;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

}