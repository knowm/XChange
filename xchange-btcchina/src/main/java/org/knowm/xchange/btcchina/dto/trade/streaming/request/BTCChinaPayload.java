package org.knowm.xchange.btcchina.dto.trade.streaming.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@JsonPropertyOrder({ "tonce", "accessKey", "requestMethod", "id", "method", "params" })
public class BTCChinaPayload {

  private final long tonce;
  private final String accessKey;
  private final String requestMethod;
  private final long id;
  private final String method;
  private final String[] params;

  public BTCChinaPayload(long tonce, String accessKey, String requestMethod, String method, String[] params) {

    this.tonce = tonce;
    this.accessKey = accessKey;
    this.requestMethod = requestMethod;
    this.id = tonce;
    this.method = method;
    this.params = params;
  }

  @JsonSerialize(using = ToStringSerializer.class)
  public long getTonce() {

    return tonce;
  }

  @JsonProperty("accesskey")
  public String getAccessKey() {

    return accessKey;
  }

  @JsonProperty("requestmethod")
  public String getRequestMethod() {

    return requestMethod;
  }

  @JsonSerialize(using = ToStringSerializer.class)
  public long getId() {

    return id;
  }

  public String getMethod() {

    return method;
  }

  public String[] getParams() {

    return params;
  }

}
