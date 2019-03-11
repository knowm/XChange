package org.knowm.xchange.bleutrade;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
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
public class BleutradeException extends RuntimeException {

  private static final long serialVersionUID = 6065661242182530213L;

  @JsonProperty("success")
  private String success;

  @JsonProperty("message")
  private String message;

  @JsonProperty("result")
  private List<Object> result = new ArrayList<Object>();

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** @return The success */
  @JsonProperty("success")
  public String getSuccess() {

    return success;
  }

  /** @param success The success */
  @JsonProperty("success")
  public void setSuccess(String success) {

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
  public List<Object> getResult() {

    return result;
  }

  /** @param result The result */
  @JsonProperty("result")
  public void setResult(List<Object> result) {

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
}
