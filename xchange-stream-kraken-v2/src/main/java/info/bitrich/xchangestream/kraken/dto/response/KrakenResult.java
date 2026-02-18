package info.bitrich.xchangestream.kraken.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class KrakenResult<V> {

  @JsonProperty("result")
  private final V result;

  @JsonProperty("error")
  private final String[] error;
}
