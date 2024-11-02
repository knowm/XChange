package info.bitrich.xchangestream.bitget.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import info.bitrich.xchangestream.bitget.config.converter.InstantToTimestampSecondsConverter;
import info.bitrich.xchangestream.bitget.dto.common.Operation;
import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class BitgetLoginRequest {

  @JsonProperty("op")
  private Operation operation;

  @Singular
  @JsonProperty("args")
  private List<LoginPayload> payloads;

  @Data
  @Builder
  @Jacksonized
  public static class LoginPayload {

    @JsonProperty("apiKey")
    private String apiKey;

    @JsonProperty("passphrase")
    private String passphrase;

    @JsonProperty("timestamp")
    @JsonSerialize(converter = InstantToTimestampSecondsConverter.class)
    private Instant timestamp;

    @JsonProperty("sign")
    private String signature;
  }
}
