package org.knowm.xchange.cexio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

// all fields gets set later by the digest.
public class CexIORequest {
  @JsonProperty("key")
  public String key;

  @JsonProperty("nonce")
  public String nonce;

  @JsonProperty("signature")
  public String signature;
}
