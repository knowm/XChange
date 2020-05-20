package org.knowm.xchange.okcoin.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MarginRepaymentResponse extends OkexResponse {

  @JsonProperty("repayment_id")
  private String repaymentId;

  @JsonProperty("client_oid")
  private String clientOid;

  public MarginRepaymentResponse() {}

  public MarginRepaymentResponse(String repaymentId, String clientOid) {
    this.repaymentId = repaymentId;
    this.clientOid = clientOid;
  }
}
