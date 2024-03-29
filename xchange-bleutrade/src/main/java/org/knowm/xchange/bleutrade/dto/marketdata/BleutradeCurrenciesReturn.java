package org.knowm.xchange.bleutrade.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"success", "message", "result"})
public class BleutradeCurrenciesReturn {

  @JsonProperty("success")
  private Boolean success;

  @JsonProperty("message")
  private String message;

  @JsonProperty("result")
  private List<BleutradeCurrency> result = new ArrayList<BleutradeCurrency>();

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonCreator
  public BleutradeCurrenciesReturn() {
    // default c-tor for jackson
  }

  /** C-tor for testing */
  public BleutradeCurrenciesReturn(List<BleutradeCurrency> currencies) {
    result = currencies;
    success = true;
  }

  /**
   * @return The success
   */
  @JsonProperty("success")
  public Boolean getSuccess() {

    return success;
  }

  /**
   * @param success The success
   */
  @JsonProperty("success")
  public void setSuccess(Boolean success) {

    this.success = success;
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
   * @return The result
   */
  @JsonProperty("result")
  public List<BleutradeCurrency> getResult() {

    return result;
  }

  /**
   * @param result The result
   */
  @JsonProperty("result")
  public void setResult(List<BleutradeCurrency> result) {

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

    return "BleutradeCurrenciesReturn [success="
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
