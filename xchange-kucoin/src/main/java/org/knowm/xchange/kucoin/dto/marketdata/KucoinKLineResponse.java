package org.knowm.xchange.kucoin.dto.marketdata;

import com.fasterxml.jackson.annotation.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"success", "code", "msg", "data"})
public class KucoinKLineResponse {

  @JsonProperty("success")
  private Boolean success;

  @JsonProperty("code")
  private String code;

  @JsonProperty("msg")
  private String msg;

  @JsonProperty("data")
  private List<List<BigDecimal>> data = null;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** No args constructor for use in serialization */
  public KucoinKLineResponse() {}

  /**
   * @param msg
   * @param code
   * @param data
   * @param success
   */
  public KucoinKLineResponse(
      Boolean success, String code, String msg, List<List<BigDecimal>> data) {
    this.success = success;
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

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

  /** @return The code */
  @JsonProperty("code")
  public String getCode() {
    return code;
  }

  /** @param code The code */
  @JsonProperty("code")
  public void setCode(String code) {
    this.code = code;
  }

  /** @return The msg */
  @JsonProperty("msg")
  public String getMsg() {
    return msg;
  }

  /** @param msg The msg */
  @JsonProperty("msg")
  public void setMsg(String msg) {
    this.msg = msg;
  }

  /** @return The data */
  @JsonProperty("data")
  public List<List<BigDecimal>> getData() {
    return data;
  }

  /** @param data The data */
  @JsonProperty("data")
  public void setData(List<List<BigDecimal>> data) {
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
