package info.bitrich.xchangestream.bitmex.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.bitmex.BitmexStreamingAdapters;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.currency.CurrencyPair;

@Data
@Builder
@AllArgsConstructor
public class RawOrderBook {

  @JsonProperty("timestamp")
  private ZonedDateTime timestamp;

  private CurrencyPair currencyPair;

  private List<PriceSizeEntry> asks;
  private List<PriceSizeEntry> bids;

  @JsonCreator
  public RawOrderBook(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("asks") List<PriceSizeEntry> asks,
      @JsonProperty("bids") List<PriceSizeEntry> bids
  ) {
    this.currencyPair = BitmexStreamingAdapters.toCurrencyPair(symbol);

    asks.forEach(priceSizeEntry -> priceSizeEntry.setSize(BitmexAdapters.scaleToLocalAmount(priceSizeEntry.getSize(), this.currencyPair.getBase())));
    bids.forEach(priceSizeEntry -> priceSizeEntry.setSize(BitmexAdapters.scaleToLocalAmount(priceSizeEntry.getSize(), this.currencyPair.getBase())));

    this.asks = asks;
    this.bids = bids;
  }

  @Data
  @Builder
  @Jacksonized
  @JsonFormat(shape = JsonFormat.Shape.ARRAY)
  public static class PriceSizeEntry {
    BigDecimal price;

    BigDecimal size;
  }
}
