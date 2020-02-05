package org.knowm.xchange.okcoin.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MarginRepaymentRequest {

  @JsonProperty("borrow_id")
  private String borrowId;

  @JsonProperty("instrument_id")
  private String instrument_id;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("amount")
  private String amount;
}
