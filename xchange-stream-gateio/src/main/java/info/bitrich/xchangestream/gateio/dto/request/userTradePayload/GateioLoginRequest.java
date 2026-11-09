package info.bitrich.xchangestream.gateio.dto.request.userTradePayload;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder
@Jacksonized
public class GateioLoginRequest {

  @JsonProperty("api_key")
  private String apiKey;

  @JsonProperty("signature")
  private String signature;

  @JsonProperty("timestamp")
  private String timestamp;

  @JsonProperty("req_id")
  private String reqId;

  @JsonProperty("req_header")
  private Map<String, String> reqHeader;
}
