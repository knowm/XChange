package org.knowm.xchange.bitfinex.v2.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@JsonInclude(Include.NON_NULL)
public class BitfinexOrdersHistoryRequest {

  @JsonProperty("id")
  List<Long> ids;

  @JsonProperty("start")
  Instant from;

  @JsonProperty("end")
  Instant to;

  @JsonProperty("limit")
  Long limit;
}
