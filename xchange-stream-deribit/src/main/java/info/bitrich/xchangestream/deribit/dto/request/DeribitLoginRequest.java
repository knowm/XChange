package info.bitrich.xchangestream.deribit.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class DeribitLoginRequest {

  @Builder.Default
  @JsonProperty("jsonrpc")
  private String jsonrpc = "2.0";

  @Builder.Default
  @JsonProperty("method")
  private Method method = Method.AUTHENTICATE;

  @JsonProperty("params")
  private Params params;

  public enum Method {
    @JsonProperty("public/auth")
    AUTHENTICATE,
  }

  @Data
  @Builder
  @Jacksonized
  public static class Params {
    @Builder.Default
    @JsonProperty("grant_type")
    private String grantType = "client_credentials";

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("client_secret")
    private String clientSecret;
  }
}
