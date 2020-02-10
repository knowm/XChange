package org.knowm.xchange.okcoin.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MarginBorrowRequest {

  @JsonProperty("instrument_id")
  private String instrumentId;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("amount")
  private String amount;

  public MarginBorrowRequest(String instrumentId, String currency, String amount) {
    this.instrumentId = instrumentId;
    this.currency = currency;
    this.amount = amount;
  }
}
