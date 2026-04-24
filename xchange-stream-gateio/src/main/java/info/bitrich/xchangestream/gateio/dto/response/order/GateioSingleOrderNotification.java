package info.bitrich.xchangestream.gateio.dto.response.order;

import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.response.GateioWsNotification;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.gateio.dto.trade.GateioSpotOrderResponse;

@Data
@SuperBuilder
@Jacksonized
public class GateioSingleOrderNotification extends GateioWsNotification {

  private GateioSpotOrderResponse result;

  @Override
  public String getUniqueChannelName() {
    String suffix =
        result.getCurrencyPair() != null
            ? Config.CHANNEL_NAME_DELIMITER + result.getCurrencyPair()
            : "";
    return super.getUniqueChannelName() + suffix;
  }
}
