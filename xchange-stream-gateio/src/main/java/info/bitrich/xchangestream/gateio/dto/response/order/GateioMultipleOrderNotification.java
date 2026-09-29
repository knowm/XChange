package info.bitrich.xchangestream.gateio.dto.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.gateio.dto.response.GateioWsNotification;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.gateio.dto.trade.GateioSpotOrderResponse;

import java.util.List;
import java.util.stream.Collectors;

@Data
@SuperBuilder
@Jacksonized
public class GateioMultipleOrderNotification extends GateioWsNotification {

  @JsonProperty("result")
  private List<GateioSpotOrderResponse> result;

  public List<GateioSingleOrderNotification> toSingleNotifications() {
    return result.stream()
        .map(
            orderResponse ->
                GateioSingleOrderNotification.builder()
                    .result(orderResponse)
                    .time(getTime())
                    .timeMs(getTimeMs())
                    .channel(getChannel())
                    .event(getEvent())
                    .error(getError())
                    .build())
        .collect(Collectors.toList());
  }
}
