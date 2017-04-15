package org.knowm.xchange.vaultoro;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"status", "data"})
public class VaultoroException extends RuntimeException {

  @JsonProperty("status")
  private String status;
  @JsonProperty("data")
  private VaultoroExceptionData data;
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
   * @return The data
   */
  @JsonProperty("data")
  public VaultoroExceptionData getData() {

    return data;
  }

  /**
   * @param data The data
   */
  @JsonProperty("data")
  public void setData(VaultoroExceptionData data) {

    this.data = data;
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
