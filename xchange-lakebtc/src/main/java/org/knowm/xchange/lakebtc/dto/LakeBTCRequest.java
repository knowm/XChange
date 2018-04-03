package org.knowm.xchange.lakebtc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import org.knowm.xchange.lakebtc.LakeBTCUtil;

/** User: cristian.lucaci Date: 10/3/2014 Time: 5:18 PM */
public class LakeBTCRequest {

  @JsonProperty("id")
  protected long id = LakeBTCUtil.getGeneratedId();

  @JsonProperty("method")
  protected String method;

  @JsonProperty("params")
  @JsonRawValue
  protected String params;

  @JsonProperty("tonce")
  private long tonce;

  @JsonProperty("accesskey")
  private String accessKey;

  @JsonProperty("requestmethod")
  private String requestMethod = "post";

  public long getId() {

    return id;
  }

  public void setId(long id) {

    this.id = id;
  }

  public String getMethod() {

    return method;
  }

  public void setMethod(String method) {

    this.method = method;
  }

  public String getParams() {

    return params;
  }

  public void setParams(String params) {

    this.params = params;
  }

  public long getTonce() {
    return tonce;
  }

  public String getAccessKey() {
    return accessKey;
  }

  public String getRequestMethod() {
    return requestMethod;
  }
}
