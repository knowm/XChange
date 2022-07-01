package org.knowm.xchange.okex.v5.dto.subaccount;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class OkexSubAccountDetails {

  @JsonProperty("enable")
  private String enable;

  @JsonProperty("subAcct")
  private String subAcct;

  @JsonProperty("label")
  private String label;

  @JsonProperty("mobile")
  private String mobile;

  @JsonProperty("gAuth")
  private String gAuth;

  @JsonProperty("ts")
  private String ts;
}
