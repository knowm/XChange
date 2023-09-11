package org.knowm.xchange.bybit.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Data
public class BybitOrderBook {

  @JsonProperty("s")
  String symbol;

  @JsonProperty("a")
  List<PriceSizeEntry> asks;

  @JsonProperty("b")
  List<PriceSizeEntry> bids;

  @JsonProperty("ts")
  Instant timestamp;

  @JsonProperty("u")
  Long updateId;



  @Data
  @Builder
  @Jacksonized
  @JsonFormat(shape = JsonFormat.Shape.ARRAY)
  public static class PriceSizeEntry {

    BigDecimal price;

    BigDecimal size;

  }
}
