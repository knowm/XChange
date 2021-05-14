package info.bitrich.xchangestream.dydx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/** Author: Max Gao (gaamox@tutanota.com) Created: 20-02-2021 */
@AllArgsConstructor
@Getter
@Setter
public class dydxWebSocketSubscriptionMessage {
  @JsonProperty("type")
  private final String type;

  @JsonProperty("channel")
  private final String channel;

  @JsonProperty("id")
  private final String id;
}
