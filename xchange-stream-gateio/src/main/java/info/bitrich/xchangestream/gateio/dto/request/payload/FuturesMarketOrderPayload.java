package info.bitrich.xchangestream.gateio.dto.request.payload;

import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder
@Jacksonized
public class FuturesMarketOrderPayload {

  private String contract;
  private BigDecimal size;
  private BigDecimal price;
  private String tif;
}
