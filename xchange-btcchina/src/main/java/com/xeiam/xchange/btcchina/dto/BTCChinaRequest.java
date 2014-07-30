package com.xeiam.xchange.btcchina.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.xeiam.xchange.btcchina.BTCChinaUtils;

/**
 * @author David Yam
 */
public class BTCChinaRequest {

  @JsonProperty("id")
  protected long id = BTCChinaUtils.getGeneratedId();

  @JsonProperty("method")
  protected String method;

  @JsonProperty("params")
  @JsonRawValue
  protected String params;

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

}
