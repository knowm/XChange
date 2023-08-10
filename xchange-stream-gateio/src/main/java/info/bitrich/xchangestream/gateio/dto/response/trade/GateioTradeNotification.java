package info.bitrich.xchangestream.gateio.dto.response.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.response.GateioWebSocketNotification;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder
@Jacksonized
public class GateioTradeNotification extends GateioWebSocketNotification {

  @JsonProperty("result")
  private TradeDTO result;

  @Override
  public String getUniqueChannelName() {
    String suffix = result.getCurrencyPair() != null ? Config.CHANNEL_NAME_DELIMITER + result.getCurrencyPair() : "";
    return super.getUniqueChannelName() + suffix;
  }
}
