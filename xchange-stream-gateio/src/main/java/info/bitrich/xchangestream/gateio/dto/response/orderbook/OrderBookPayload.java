package info.bitrich.xchangestream.gateio.dto.response.orderbook;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.gateio.config.converter.StringToCurrencyPairConverter;

@Data
public class OrderBookPayload {

  @JsonProperty("t")
  Instant timestamp;

  @JsonProperty("lastUpdateId")
  Long lastUpdateId;

  @JsonProperty("s")
  @JsonDeserialize(converter = StringToCurrencyPairConverter.class)
  CurrencyPair currencyPair;


  @JsonProperty("asks")
  List<PriceSizeEntry> asks;

  @JsonProperty("bids")
  List<PriceSizeEntry> bids;


  @Data
  @Builder
  @Jacksonized
  @JsonFormat(shape = JsonFormat.Shape.ARRAY)
  public static class PriceSizeEntry {

    BigDecimal price;

    BigDecimal size;

  }


}
