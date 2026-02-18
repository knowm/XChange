package org.knowm.xchange.dase.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Request body for placing an order (limit or market). */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DasePlaceOrderInput {

  @JsonProperty("market")
  public String market; // required

  @JsonProperty("type")
  public String type; // "limit" | "market"

  @JsonProperty("side")
  public String side; // "buy" | "sell"

  @JsonProperty("size")
  public String size; // string, optional (required for limit; optional for market)

  @JsonProperty("price")
  public String price; // string, required for limit

  @JsonProperty("funds")
  public String funds; // string, optional (market only)

  @JsonProperty("post_only")
  public Boolean postOnly; // optional

  @JsonProperty("client_id")
  public String clientId; // optional
}
