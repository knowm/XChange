package info.bitrich.xchangestream.gateio.dto.response.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.response.GateioWsNotification;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@SuperBuilder
@Jacksonized
public class GateioFuturesTradeNotification extends GateioWsNotification {

  @JsonProperty("result")
  private List<TradeFuturesPayload> result;

  @Override
  public String getUniqueChannelName() {
    String suffix =
        result.get(0).contract != null
            ? Config.CHANNEL_NAME_DELIMITER + result.get(0).contract
            : "";
    return super.getUniqueChannelName() + suffix;
  }
}
