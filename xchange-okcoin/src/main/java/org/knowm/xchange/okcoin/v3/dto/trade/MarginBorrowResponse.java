package org.knowm.xchange.okcoin.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MarginBorrowResponse extends OkexResponse {

  @JsonProperty("borrow_id")
  private String borrowId;

  @JsonProperty("client_oid")
  private String clientOid;

  public MarginBorrowResponse() {}

  public MarginBorrowResponse(String borrowId, String clientOid) {
    this.borrowId = borrowId;
    this.clientOid = clientOid;
  }
}
