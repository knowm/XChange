package org.knowm.xchange.deribit.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DeribitAuthentication implements ParamsDigest {

  /** Authorization type, allowed value - bearer */
  @JsonProperty("token_type")
  private String tokenType;

  /** Type of the access for assigned token */
  @JsonProperty("scope")
  private String scope;

  /** Can be used to request a new token (with a new lifetime) */
  @JsonProperty("refresh_token")
  private String refreshToken;

  /** Token lifetime in seconds */
  @JsonProperty("expires_in")
  private long expiresIn;

  @JsonProperty("access_token")
  private String accessToken;

  /** Copied from the input (if applicable) */
  @JsonProperty("state")
  private String state;

  private final long created = System.currentTimeMillis();

  public boolean valid() {
    return System.currentTimeMillis()
        < created + (expiresIn - 5) * 1000; // lets have 5 seconds 'safety margin'
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {
    return "Bearer " + accessToken;
  }
}
