package info.bitrich.xchangestream.gateio.dto.response.balance;

import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.response.GateioWebSocketNotification;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class GateioSingleSpotBalanceNotification extends GateioWebSocketNotification {

  private BalanceDTO result;

  @Override
  public String getUniqueChannelName() {
    // there is no currency specific subscription
    return super.getUniqueChannelName() + Config.CHANNEL_NAME_DELIMITER + "null";
  }


}
