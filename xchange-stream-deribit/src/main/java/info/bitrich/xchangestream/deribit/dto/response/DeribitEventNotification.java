package info.bitrich.xchangestream.deribit.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder(toBuilder = true)
@Jacksonized
public class DeribitEventNotification extends DeribitWsNotification<Object> {

  @JsonProperty("result")
  private Object result;

  public boolean hasToken() {
    return result instanceof Map && ((Map) result).containsKey("access_token");
  }
}
