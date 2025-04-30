package org.knowm.xchange.coinex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.coinex.config.converter.StringToCurrencyPairConverter;
import org.knowm.xchange.currency.CurrencyPair;

@Data
@Builder
@Jacksonized
public class CoinexMarketDepth {

  @JsonProperty("depth")
  Depth depth;

  @JsonProperty("is_full")
  Boolean isFull;

  @JsonProperty("market")
  @JsonDeserialize(converter = StringToCurrencyPairConverter.class)
  CurrencyPair currencyPair;

  @Data
  @Builder
  @Jacksonized
  public static class Depth {

    @JsonProperty("asks")
    List<PriceSizeEntry> asks;

    @JsonProperty("bids")
    List<PriceSizeEntry> bids;

    @JsonProperty("checksum")
    Long checksum;

    @JsonProperty("updated_at")
    Instant updatedAt;
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
