package info.bitrich.xchangestream.gateio.dto.response.orderbook;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.response.GateioWsNotification;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder
@Jacksonized
public class GateioOrderBookSpotTickerNotification extends GateioWsNotification {
  @JsonProperty("result")
  private GateioOrderBookSpotTickerResponse result;

  @Override
  public String getUniqueChannelName() {
    String suffix =
        result.getContract() != null
            ? Config.CHANNEL_NAME_DELIMITER + result.getContract()
            : "";
    return super.getUniqueChannelName() + suffix;
  }
}
