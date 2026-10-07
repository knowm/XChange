package info.bitrich.xchangestream.gateio.dto.response.wsPlaceOrder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@lombok.Data
@SuperBuilder
@Jacksonized
public class GateioWsResponseData<T> {

  @JsonProperty("result")
  private T result;

  @JsonProperty("errs")
  private GateioWsErrs errs;
}

