package org.knowm.xchange.okex.v5.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/** https://www.okx.com/docs-v5/en/#rest-api-account-set-leverage */
@Getter
@NoArgsConstructor
@ToString
public class OkexSetLeverageResponse {

  @JsonProperty("mgnMode")
  private String marginMode;

  @JsonProperty("posSide")
  private String positionSide;

  @JsonProperty("instId")
  private String instrumentId;

  @JsonProperty("lever")
  private String leverage;
}
