package info.bitrich.xchangestream.gateio.dto.request.payload;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Data
@SuperBuilder
@Jacksonized
public class FuturesMarketOrderPayload {

  private String contract;
  private BigDecimal size;
  private BigDecimal price;
  private String tif;
}