package org.knowm.xchange.binance.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.binance.config.converter.StringToCurrencyPairConverter;
import org.knowm.xchange.currency.CurrencyPair;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
public final class BinanceTicker24h {

  private BigDecimal priceChange;
  private BigDecimal priceChangePercent;
  private BigDecimal weightedAvgPrice;
  private BigDecimal prevClosePrice;
  private BigDecimal lastPrice;
  private BigDecimal lastQty;
  private BigDecimal bidPrice;
  private BigDecimal bidQty;
  private BigDecimal askPrice;
  private BigDecimal askQty;
  private BigDecimal openPrice;
  private BigDecimal highPrice;
  private BigDecimal lowPrice;
  private BigDecimal volume;
  private BigDecimal quoteVolume;
  private long openTime;
  private long closeTime;
  private long firstId;
  private long lastId;
  private long count;

  @JsonProperty("symbol")
  @JsonDeserialize(converter = StringToCurrencyPairConverter.class)
  private CurrencyPair currencyPair;

  public boolean isValid() {
    return currencyPair != null;
  }
}
