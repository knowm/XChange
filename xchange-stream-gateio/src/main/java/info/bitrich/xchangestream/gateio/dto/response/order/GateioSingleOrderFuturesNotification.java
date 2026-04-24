package info.bitrich.xchangestream.gateio.dto.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.response.GateioWsNotification;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.gateio.dto.trade.GateioFuturesOrderResponse;

import java.util.List;

@Data
@SuperBuilder
@Jacksonized
public class GateioSingleOrderFuturesNotification extends GateioWsNotification {
  @JsonProperty("result")
  private List<GateioFuturesOrderResponse> result;

  @Override
  public String getUniqueChannelName() {
    String suffix =
        result.get(0).getContract() != null
            ? Config.CHANNEL_NAME_DELIMITER + result.get(0).getContract()
            : "";
    return super.getUniqueChannelName() + suffix;
  }
}
