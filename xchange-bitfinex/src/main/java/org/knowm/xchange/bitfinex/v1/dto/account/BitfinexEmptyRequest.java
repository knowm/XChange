package org.knowm.xchange.bitfinex.v1.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;

public class BitfinexEmptyRequest {

  @JsonProperty("request")
  protected String request;

  @JsonProperty("nonce")
  protected String nonce;

  @JsonProperty("options")
  @JsonRawValue
  protected String options;

  /**
   * Constructor
   *
   * @param nonce
   */
  public BitfinexEmptyRequest(String nonce, String request) {

    this.request = request;
    this.nonce = String.valueOf(nonce);
    this.options = "[]";
  }

  public String getRequest() {

    return request;
  }

  public void setRequest(String request) {

    this.request = request;
  }

  public String getNonce() {

    return nonce;
  }

  public void setNonce(String nonce) {

    this.nonce = nonce;
  }

  public String getOptions() {

    return options;
  }

  public void setOptions(String options) {

    this.options = options;
  }
}
