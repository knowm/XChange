package info.bitrich.xchangestream.gateio.dto.request.payload;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder
@Jacksonized
public class StringPayload {

  private String data;

}
