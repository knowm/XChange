package org.knowm.xchange.vaultoro.dto.trade;

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
@JsonPropertyOrder({"status", "data"})
public class VaultoroCancelOrderResponse {

  @JsonProperty("status")
  private String status;

  @JsonProperty("data")
  private VaultoroCancelOrderData data;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** @return The status */
  @JsonProperty("status")
  public String getStatus() {

    return status;
  }

  /** @param status The status */
  @JsonProperty("status")
  public void setStatus(String status) {

    this.status = status;
  }

  /** @return The data */
  @JsonProperty("data")
  public VaultoroCancelOrderData getData() {

    return data;
  }

  /** @param data The data */
  @JsonProperty("data")
  public void setData(VaultoroCancelOrderData data) {

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
