package info.bitrich.xchangestream.gateio.dto.response.balance;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.gateio.dto.response.GateioWsNotification;
import java.util.List;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder
@Jacksonized
@Data
public class GateioFuturesBalanceNotification extends GateioWsNotification {

  @JsonProperty("result")
  private List<GateioFuturesBalance> result;
}
