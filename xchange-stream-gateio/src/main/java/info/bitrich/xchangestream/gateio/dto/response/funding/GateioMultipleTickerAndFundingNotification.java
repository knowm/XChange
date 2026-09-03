package info.bitrich.xchangestream.gateio.dto.response.funding;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.gateio.dto.response.GateioWsNotification;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.gateio.dto.marketdata.GateioFuturesTickerAndFunding;

import java.util.List;
import java.util.stream.Collectors;

@Data
@SuperBuilder
@Jacksonized
public class GateioMultipleTickerAndFundingNotification extends GateioWsNotification {

  @JsonProperty("result")
  private List<GateioFuturesTickerAndFunding> result;

  public List<GateioSingleTickerAndFundingNotification> toSingleNotifications() {
    return result.stream()
        .map(
            fundingResponse ->
                GateioSingleTickerAndFundingNotification.builder()
                    .result(fundingResponse)
                    .time(getTime())
                    .timeMs(getTimeMs())
                    .channel(getChannel())
                    .event(getEvent())
                    .error(getError())
                    .build())
        .collect(Collectors.toList());
  }

}
