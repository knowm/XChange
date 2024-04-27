package org.knowm.xchange.gateio.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.gateio.config.converter.TimestampSecondsToInstantConverter;

@Data
@Builder
@Jacksonized
public class GateioCurrencyPairDetails {

  @JsonProperty("id")
  String id;

  @JsonProperty("base")
  String asset;

  @JsonProperty("quote")
  String quote;

  @JsonProperty("fee")
  BigDecimal fee;

  @JsonProperty("min_base_amount")
  BigDecimal minAssetAmount;

  @JsonProperty("min_quote_amount")
  BigDecimal minQuoteAmount;

  @JsonProperty("amount_precision")
  Integer assetScale;

  @JsonProperty("precision")
  Integer quoteScale;

  @JsonProperty("trade_status")
  String tradeStatus;

  @JsonProperty("sell_start")
  @JsonDeserialize(converter = TimestampSecondsToInstantConverter.class)
  Instant startOfSells;

  @JsonProperty("buy_start")
  @JsonDeserialize(converter = TimestampSecondsToInstantConverter.class)
  Instant startOfBuys;

}
