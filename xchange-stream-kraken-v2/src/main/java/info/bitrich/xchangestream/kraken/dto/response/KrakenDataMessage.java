package info.bitrich.xchangestream.kraken.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder(toBuilder = true)
@Jacksonized
public class KrakenDataMessage<T> extends KrakenMessage {

  @JsonProperty("data")
  private List<T> data;

  /**
   * @return first element of data array
   */
  @JsonIgnore
  public T getPayload() {
    if (data != null && !data.isEmpty()) {
      return data.get(0);
    }
    return null;
  }
}
