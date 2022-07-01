package org.knowm.xchange.okcoin.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FuturesMultipleOrderCancellationResponse extends OkexResponse {

  @JsonProperty("instrument_id")
  private String instrumentId;

  /** Order IDs */
  @JsonProperty("order_ids")
  private List<String> orderIds;

  /** the order IDs created by yourself */
  @JsonProperty("client_oids")
  private List<String> clientOids;
}
