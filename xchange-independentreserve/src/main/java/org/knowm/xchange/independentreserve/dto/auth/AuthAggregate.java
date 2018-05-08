package org.knowm.xchange.independentreserve.dto.auth;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.LinkedHashMap;
import java.util.Map;

/** Author: Kamil Zbikowski Date: 4/13/15 */
public class AuthAggregate {
  private final String apiKey;
  private final Long nonce;
  protected Map<String, Object> parameters;
  private String signature;

  public AuthAggregate(String apiKey, Long nonce) {
    this.apiKey = apiKey;
    this.nonce = nonce;
    this.parameters = new LinkedHashMap<>();
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
  public Map<String, Object> getParameters() {
    return parameters;
  }

  @JsonAnySetter
  public void setParameters(Map<String, Object> parameters) {
    this.parameters = parameters;
  }
}
