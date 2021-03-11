package info.bitrich.xchangestream.dydx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Author: Max Gao (gaamox@tutanota.com) Created: 20-02-2021 */
@NoArgsConstructor
@Getter
@Setter
public abstract class dydxWebSocketTransaction {
  @JsonProperty("type")
  private String type;

  @JsonProperty("connection_id")
  private String connectionId;

  @JsonProperty("message_id")
  private String messageId;

  @JsonProperty("channel")
  private String channel;

  @JsonProperty("id")
  private String id;
}
