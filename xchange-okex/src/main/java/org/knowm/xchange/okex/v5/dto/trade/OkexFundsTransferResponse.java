package org.knowm.xchange.okex.v5.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class OkexFundsTransferResponse {
  @JsonProperty("transId")
  private String transId;

  @JsonProperty("clientId")
  private String clientId;

  @JsonProperty("ccy")
  private String ccy;

  @JsonProperty("from")
  private String from;

  @JsonProperty("amt")
  private String amt;

  @JsonProperty("to")
  private String to;
}
