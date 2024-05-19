package org.knowm.xchange.gateio.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.gateio.config.converter.StringToCurrencyPairConverter;

@Data
@Builder
@Jacksonized
public class GateioTicker {

  @JsonProperty("currency_pair")
  @JsonDeserialize(converter = StringToCurrencyPairConverter.class)
  CurrencyPair currencyPair;

  @JsonProperty("last")
  BigDecimal lastPrice;

  @JsonProperty("lowest_ask")
  BigDecimal lowestAsk;

  @JsonProperty("highest_bid")
  BigDecimal highestBid;

  @JsonProperty("change_percentage")
  BigDecimal changePercentage24h;

  @JsonProperty("change_utc0")
  BigDecimal changePercentage24hUTC0;

  @JsonProperty("change_utc8")
  BigDecimal changePercentage24hUTC8;

  @JsonProperty("base_volume")
  BigDecimal assetVolume;

  @JsonProperty("quote_volume")
  BigDecimal quoteVolume;

  @JsonProperty("high_24h")
  BigDecimal maxPrice24h;

  @JsonProperty("low_24h")
  BigDecimal minPrice24h;

  @JsonProperty("etf_net_value")
  BigDecimal etfNetValue;

  @JsonProperty("etf_pre_net_value")
  BigDecimal etfPreNetValue;

  @JsonProperty("etf_pre_timestamp")
  Instant etfPreTimestamp;

  @JsonProperty("etf_leverage")
  BigDecimal etfLeverage;

}
