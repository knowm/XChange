package info.bitrich.xchangestream.gateio.dto.response.usertrade;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.gateio.dto.response.GateioWsNotification;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder
@Jacksonized
public class GateioMultipleUserTradeNotification extends GateioWsNotification {

  @JsonProperty("result")
  private List<UserTradePayload> result;


  public List<GateioSingleUserTradeNotification> toSingleNotifications() {
    return result.stream()
        .map(userTradePayload -> GateioSingleUserTradeNotification.builder()
            .result(userTradePayload)
            .time(getTime())
            .timeMs(getTimeMs())
            .channel(getChannel())
            .event(getEvent())
            .error(getError())
            .build())
        .collect(Collectors.toList());
  }
}
