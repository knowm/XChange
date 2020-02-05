package org.knowm.xchange.okcoin.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MarginSetLeverageRequest {

  @JsonProperty("instrument_id")
  private String instrumentId;

  @JsonProperty("leverage")
  private String leverage;
}
