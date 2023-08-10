package info.bitrich.xchangestream.gateio.dto.response.usertrade;

import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.response.GateioWebSocketNotification;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class GateioSingleUserTradeNotification extends GateioWebSocketNotification {

  private UserTradeDTO result;

  @Override
  public String getUniqueChannelName() {
    String suffix = result.getCurrencyPair() != null ? Config.CHANNEL_NAME_DELIMITER + result.getCurrencyPair() : "";
    return super.getUniqueChannelName() + suffix;
  }
}
