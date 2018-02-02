package org.knowm.xchange.kucoin.dto;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class KucoinSimpleResponse<D> {

  @JsonProperty("success")
  private Boolean success;
  @JsonProperty("code")
  private String code;
  @JsonProperty("data")
  private D data;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();


  /**
   * No args constructor for use in serialization
   * 
   */
  public KucoinSimpleResponse() {
  }

  public KucoinSimpleResponse(Boolean success, String code, D data) {
    super();
    this.success = success;
    this.code = code;
    this.data = data;
  }

  /**
   * 
   * @return
   *     The success
   */
  @JsonProperty("success")
  public Boolean isSuccess() {
      return success;
  }

  /**
   * 
   * @param success
   *     The success
   */
  @JsonProperty("success")
  public void setSuccess(Boolean success) {
      this.success = success;
  }

  /**
   * 
   * @return
   *     The code
   */
  @JsonProperty("code")
  public String getCode() {
      return code;
  }

  /**
   * 
   * @param code
   *     The code
   */
  @JsonProperty("code")
  public void setCode(String code) {
      this.code = code;
  }

  /**
   * 
   * @return
   *     The data
   */
  @JsonProperty("data")
  public D getData() {
      return data;
  }

  /**
   * 
   * @param data
   *     The data
   */
  @JsonProperty("data")
  public void setData(D data) {
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
