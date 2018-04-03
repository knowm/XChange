package org.knowm.xchange.bleutrade.dto.account;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"success", "message", "result"})
public class BleutradeBalanceReturn {

  @JsonProperty("success")
  private Boolean success;

  @JsonProperty("message")
  private String message;

  @JsonProperty("result")
  private BleutradeBalance result;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** @return The success */
  @JsonProperty("success")
  public Boolean getSuccess() {

    return success;
  }

  /** @param success The success */
  @JsonProperty("success")
  public void setSuccess(Boolean success) {

    this.success = success;
  }

  /** @return The message */
  @JsonProperty("message")
  public String getMessage() {

    return message;
  }

  /** @param message The message */
  @JsonProperty("message")
  public void setMessage(String message) {

    this.message = message;
  }

  /** @return The result */
  @JsonProperty("result")
  public BleutradeBalance getResult() {

    return result;
  }

  /** @param result The result */
  @JsonProperty("result")
  public void setResult(BleutradeBalance result) {

    this.result = result;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {

    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {

    this.additionalProperties.put(name, value);
  }

  @Override
  public String toString() {

    return "BleutradeBalanceReturn [success="
        + success
        + ", message="
        + message
        + ", result="
        + result
        + ", additionalProperties="
        + additionalProperties
        + "]";
  }
}
