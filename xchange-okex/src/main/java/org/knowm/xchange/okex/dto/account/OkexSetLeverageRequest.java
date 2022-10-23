package org.knowm.xchange.okex.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.ToString;

/** https://www.okx.com/docs-v5/en/#rest-api-account-set-leverage */
@Builder
@ToString
public class OkexSetLeverageRequest {

  @JsonProperty("instId")
  private String instrumentId;

  @JsonProperty("ccy")
  private String currency;

  @JsonProperty("lever")
  private String leverage;

  @JsonProperty("mgnMode")
  private String marginMode;

  @JsonProperty("posSide")
  private String positionSide;
}
