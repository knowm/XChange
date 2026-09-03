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
public class GateioOrderBookV2FuturesNotification extends GateioWsNotification {

  @JsonProperty("result")
  private OrderBookV2FuturesResponse result;

  @Override
  public String getUniqueChannelName() {
    String suffix =
        result.getInstrument() != null
            ? Config.CHANNEL_NAME_DELIMITER + result.getInstrument()
            : "";
    return super.getUniqueChannelName() + suffix;
  }
}
