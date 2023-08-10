package info.bitrich.xchangestream.gateio.dto.response.balance;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.gateio.dto.response.GateioWebSocketNotification;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder
@Jacksonized
public class GateioMultipleSpotBalanceNotification extends GateioWebSocketNotification {

  @JsonProperty("result")
  private List<BalanceDTO> result;


  public List<GateioSingleSpotBalanceNotification> toSingleNotifications() {
    return result.stream()
        .map(balanceDTO -> GateioSingleSpotBalanceNotification.builder()
            .result(balanceDTO)
            .time(getTime())
            .timeMs(getTimeMs())
            .channel(getChannel())
            .event(getEvent())
            .error(getError())
            .build())
        .collect(Collectors.toList());
  }
}
