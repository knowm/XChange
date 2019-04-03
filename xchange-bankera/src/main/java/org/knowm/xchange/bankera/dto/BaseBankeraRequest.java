package org.knowm.xchange.bankera.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BaseBankeraRequest {

  @JsonProperty("nonce")
  private final Long nonce;

  public BaseBankeraRequest(Long nonce) {
    this.nonce = nonce;
  }
}
