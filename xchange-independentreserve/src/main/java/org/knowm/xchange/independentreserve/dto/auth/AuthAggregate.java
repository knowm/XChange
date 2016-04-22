package org.knowm.xchange.independentreserve.dto.auth;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

/**
 * Author: Kamil Zbikowski Date: 4/13/15
 */
public class AuthAggregate {
  private final String apiKey;
  private String signature;
  private final Long nonce;

  protected Map<String, String> parameters;

  public AuthAggregate(String apiKey, Long nonce) {
    this.apiKey = apiKey;
    this.nonce = nonce;
    this.parameters = new LinkedHashMap<String, String>();
  }

  public String getApiKey() {
    return apiKey;
  }

  public Long getNonce() {
    return nonce;
  }

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  @JsonAnyGetter
  public Map<String, String> getParameters() {
    return parameters;
  }

  @JsonAnySetter
  public void setParameters(Map<String, String> parameters) {
    this.parameters = parameters;
  }
}
