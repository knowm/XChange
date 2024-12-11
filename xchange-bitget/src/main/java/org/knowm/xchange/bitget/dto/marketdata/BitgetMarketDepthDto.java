package org.knowm.xchange.bitget.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class BitgetMarketDepthDto {

  @JsonProperty("asks")
  List<PriceSizeEntry> asks;

  @JsonProperty("bids")
  List<PriceSizeEntry> bids;

  @JsonProperty("ts")
  Instant timestamp;

  @Data
  @Builder
  @Jacksonized
  @JsonFormat(shape = JsonFormat.Shape.ARRAY)
  public static class PriceSizeEntry {
    BigDecimal price;

    BigDecimal size;
  }
}
