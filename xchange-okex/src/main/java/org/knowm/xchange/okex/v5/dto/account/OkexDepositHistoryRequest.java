package org.knowm.xchange.okex.v5.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OkexDepositHistoryRequest {

  @JsonProperty("ccy")
  private String currency;

  @JsonProperty("depId")
  private String depId;

  @JsonProperty("txId")
  private String txId;

  @JsonProperty("state")
  private String state;

  @JsonProperty("after")
  private String after;

  @JsonProperty("before")
  private String before;

  @JsonProperty("limit")
  private String limit;
}
