package org.knowm.xchange.okex.v5.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OkexFundsTransferRequest {

  @JsonProperty("ccy")
  private final String ccy;

  @JsonProperty("amt")
  private final String amt;

  @JsonProperty("from")
  private final String from;

  @JsonProperty("to")
  private final String to;

  @JsonProperty("subAcct")
  private final String subAcct;

  @JsonProperty("type")
  private final String type;

  @JsonProperty("loanTrans")
  private final boolean loanTrans;

  @JsonProperty("clientId")
  private final String clientId;

  @JsonProperty("omitPosRisk")
  private final String omitPosRisk;
}
