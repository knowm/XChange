package org.knowm.xchange.dase.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Optional request body for DELETE /v1/orders/ (cancel all). */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DaseCancelAllOrdersQuery {

  @JsonProperty("market")
  public String market; // optional: cancel all for a market, if supported by API
}
