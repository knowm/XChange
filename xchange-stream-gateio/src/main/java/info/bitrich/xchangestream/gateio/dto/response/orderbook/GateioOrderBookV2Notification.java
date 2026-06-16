package info.bitrich.xchangestream.gateio.dto.response.orderbook;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.response.GateioWsNotification;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Jacksonized
public class GateioOrderBookV2Notification extends GateioWsNotification {

  @JsonProperty("result")
  private OrderBookV2Response result;

  @Override
  public String getUniqueChannelName() {
    String suffix =
        result.getCurrencyPair() != null
            ? Config.CHANNEL_NAME_DELIMITER + result.getContract()
            : "";
    return super.getUniqueChannelName() + suffix;
  }
}
