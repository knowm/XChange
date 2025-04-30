package org.knowm.xchange.binance.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.apache.commons.lang3.ObjectUtils;
import org.knowm.xchange.binance.config.converter.StringToCurrencyPairConverter;
import org.knowm.xchange.currency.CurrencyPair;

@Data
@Builder
@Jacksonized
public final class BinancePriceQuantity {

  @JsonProperty("symbol")
  @JsonDeserialize(converter = StringToCurrencyPairConverter.class)
  CurrencyPair currencyPair;

  @JsonProperty("bidPrice")
  BigDecimal bidPrice;

  @JsonProperty("bidQty")
  BigDecimal bidQty;

  @JsonProperty("askPrice")
  BigDecimal askPrice;

  @JsonProperty("askQty")
  BigDecimal askQty;

  public boolean isValid() {
    return ObjectUtils.allNotNull(currencyPair, bidPrice, bidQty, askPrice, askQty);
  }
}
