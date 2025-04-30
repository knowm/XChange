package info.bitrich.xchangestream.bitget.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.bitget.dto.common.BitgetChannel;
import info.bitrich.xchangestream.bitget.dto.common.Operation;
import java.util.List;
import lombok.Data;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder
@Jacksonized
public class BitgetWsRequest {

  @JsonProperty("op")
  private Operation operation;

  @Singular
  @JsonProperty("args")
  private List<BitgetChannel> channels;
}
