package info.bitrich.xchangestream.kraken.dto.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder(toBuilder = true)
@Jacksonized
public class KrakenHeartbeatMessage extends KrakenMessage {

  @Override
  public String getChannelId() {
    // no subscription for hearbeat channel
    return null;
  }
}
