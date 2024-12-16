package info.bitrich.xchangestream.bitmex.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.bitmex.BitmexStreamingAdapters;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.currency.CurrencyPair;


@Data
@Builder
@AllArgsConstructor
public class BitmexTicker {

  @JsonProperty("timestamp")
  private ZonedDateTime timestamp;

  private CurrencyPair currencyPair;

  private  BigDecimal bidSize;

  @JsonProperty("bidPrice")
  private BigDecimal bidPrice;

  @JsonProperty("askPrice")
  private BigDecimal askPrice;

  private BigDecimal askSize;

  @JsonCreator
  public BitmexTicker(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("bidSize") BigDecimal bidSize,
      @JsonProperty("askSize") BigDecimal askSize) {
    this.currencyPair = BitmexStreamingAdapters.toInstrument(symbol);

    // scale values
    this.bidSize = BitmexAdapters.scaleToLocalAmount(bidSize, currencyPair.getBase());
    this.askSize = BitmexAdapters.scaleToLocalAmount(askSize, currencyPair.getBase());
  }


}
