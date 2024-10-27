package info.bitrich.xchangestream.bitget.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder
@Jacksonized
public class BitgetWsOrderBookSnapshotNotification extends BitgetWsNotification {

  @JsonProperty("data")
  private List<OrderBookData> data;


  @Data
  @Builder
  @Jacksonized
  public static class OrderBookData {

    @JsonProperty("asks")
    private List<PriceSizeEntry> asks;

    @JsonProperty("bids")
    private List<PriceSizeEntry> bids;

    @JsonProperty("checksum")
    private Long checksum;

    @JsonProperty("ts")
    private Instant timestamp;

    @Data
    @Builder
    @Jacksonized
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    public static class PriceSizeEntry {

      private BigDecimal price;

      private BigDecimal size;
    }
  }
}
