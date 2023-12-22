package org.knowm.xchange.okex.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.ToString;

/** https://www.okx.com/docs-v5/en/#rest-api-funding-withdrawal */
@Builder
@ToString
public class OkexWithdrawalRequest {
  @JsonProperty("ccy")
  private String currency;

  @JsonProperty("amt")
  private String amount;

  // 3: internal, 4: on chain
  @JsonProperty("dest")
  private String method;

  @JsonProperty("toAddr")
  private String address;

  @JsonProperty("fee")
  private String fee;

  @JsonProperty("chain")
  private String chain;

  @JsonProperty("clientId")
  private String clientId;
}
