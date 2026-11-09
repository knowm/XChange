package info.bitrich.xchangestream.coinjar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;

public class CoinjarHeartbeat {

  @JsonProperty("topic")
  public final String topic = "phoenix";

  @JsonProperty("event")
  public final String event = "heartbeat";

  @JsonProperty("payload")
  @JsonInclude(JsonInclude.Include.ALWAYS)
  public final Map<String, String> payload = new HashMap<>();

  @JsonProperty("ref")
  public final Integer ref;

  public CoinjarHeartbeat(Integer ref) {
    this.ref = ref;
  }
}
