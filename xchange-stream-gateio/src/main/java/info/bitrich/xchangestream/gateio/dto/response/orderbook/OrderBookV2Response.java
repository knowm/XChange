package info.bitrich.xchangestream.gateio.dto.response.orderbook;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.gateio.config.converter.StringToInstrumentConverter;
import org.knowm.xchange.instrument.Instrument;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderBookV2Response {

  @JsonProperty("t")
  private Instant timestamp;

  @JsonProperty("full")
  private boolean full;

  @JsonProperty("s")
  private String streamName;

  @JsonProperty("U")
  private Long firstUpdateId;

  @JsonProperty("u")
  private Long lastUpdateId;

  @JsonProperty("asks")
  @JsonAlias("a")
  private List<PriceSizeEntry> asks = new ArrayList<>();

  @JsonProperty("bids")
  @JsonAlias("b")
  private List<PriceSizeEntry> bids = new ArrayList<>();

  @JsonProperty("contract")
  @JsonDeserialize(converter = StringToInstrumentConverter.class)
  Instrument contract;

  @JsonIgnore
  public CurrencyPair getCurrencyPair() {
    if (streamName == null || !streamName.startsWith("ob.")) {
      return null;
    }

    String[] parts = streamName.substring(3).split("\\.");
    if (parts.length < 2) {
      return null;
    }

    String[] currencies = parts[0].split("_");
    if (currencies.length != 2) {
      return null;
    }

    return new CurrencyPair(currencies[0], currencies[1]);
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
