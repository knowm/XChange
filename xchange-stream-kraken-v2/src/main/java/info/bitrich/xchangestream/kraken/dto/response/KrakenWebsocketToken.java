package info.bitrich.xchangestream.kraken.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class KrakenWebsocketToken {

  @JsonProperty("token")
  private String token;

  @JsonProperty("expires")
  private Integer expiresInSeconds;
}
