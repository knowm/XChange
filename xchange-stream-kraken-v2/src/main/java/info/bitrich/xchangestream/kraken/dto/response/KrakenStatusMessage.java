package info.bitrich.xchangestream.kraken.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.kraken.dto.response.KrakenStatusMessage.Payload;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder(toBuilder = true)
@Jacksonized
public class KrakenStatusMessage extends KrakenDataMessage<Payload> {

  @Override
  public String getChannelId() {
    // no subscription for status channel
    return null;
  }

  @Data
  @Builder
  @Jacksonized
  public static class Payload {
    @JsonProperty("version")
    private String version;

    @JsonProperty("system")
    private Status status;

    @JsonProperty("api_version")
    private String apiVersion;

    @JsonProperty("connection_id")
    private String connectionId;
  }

  public enum Status {
    @JsonProperty("online")
    ONLINE,

    @JsonProperty("cancel_only")
    CANCEL_ONLY,

    @JsonProperty("maintenance")
    MAINTENANCE,

    @JsonProperty("post_only")
    POST_ONLY,
  }
}
