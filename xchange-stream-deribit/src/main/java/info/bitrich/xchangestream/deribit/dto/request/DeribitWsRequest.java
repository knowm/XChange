package info.bitrich.xchangestream.deribit.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder
@Jacksonized
public class DeribitWsRequest {

  @Builder.Default
  @JsonProperty("jsonrpc")
  private String jsonrpc = "2.0";

  @JsonProperty("method")
  private Method method;

  @JsonProperty("params")
  private Params params;

  public enum Method {
    @JsonProperty("public/subscribe")
    SUBSCRIBE,

    @JsonProperty("public/unsubscribe")
    UNSUBSCRIBE,
  }

  @Data
  @Builder
  @Jacksonized
  public static class Params {
    @JsonProperty("channels")
    private List<String> channels;
  }
}
