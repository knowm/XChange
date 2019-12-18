package org.knowm.xchange.okcoin.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class OrderCancellationRequest {

  /**
   * required By providing this parameter, the corresponding order of a designated trading pair will
   * be cancelled. If not providing this parameter, it will be back to error code.
   */
  @JsonProperty("instrument_id")
  private String instrumentId;
}
