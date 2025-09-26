package dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BybitOrderMessage<T> {
  private final String reqId;
  private final Header header;
  private final String op;
  private final List<T> args;

  @Getter
  @AllArgsConstructor
  public static class Header {
    @JsonProperty("X-BAPI-TIMESTAMP")
    private String X_BAPI_TIMESTAMP;
    @JsonProperty("X-BAPI-RECV-WINDOW")
    private String X_BAPI_RECV_WINDOW;
    @JsonProperty("Referer")
    private String referer;
  }
}
