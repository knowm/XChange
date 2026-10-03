package info.bitrich.xchangestream.gateio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.gateio.dto.response.wsPlaceOrder.GateioWsResponseData;
import info.bitrich.xchangestream.gateio.dto.response.wsPlaceOrder.GateioWsResponseHeader;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@lombok.Data
@SuperBuilder
@Jacksonized
public class GateioUserTradeWsResponse<T> {

  @JsonProperty("request_id")
  private String requestId;

  @JsonProperty("header")
  private GateioWsResponseHeader header;

  @JsonProperty("data")
  private GateioWsResponseData<T> data;

}