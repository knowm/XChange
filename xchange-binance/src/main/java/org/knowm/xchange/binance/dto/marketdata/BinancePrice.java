package org.knowm.xchange.binance.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.binance.config.converter.StringToCurrencyPairConverter;
import org.knowm.xchange.currency.CurrencyPair;

@Data
@Builder
@Jacksonized
public class BinancePrice {

  @JsonProperty("symbol")
  @JsonDeserialize(converter = StringToCurrencyPairConverter.class)
  CurrencyPair currencyPair;

  @JsonProperty("price")
  BigDecimal price;

  public boolean isValid() {
    return currencyPair != null && price != null;
  }
}
