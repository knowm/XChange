package org.knowm.xchange.bitfinex.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitfinex.config.converter.StringToCurrencyPairConverter;
import org.knowm.xchange.currency.CurrencyPair;

@Data
@Builder
@Jacksonized
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class BitfinexCurrencyPairInfo {

  @JsonDeserialize(converter = StringToCurrencyPairConverter.class)
  CurrencyPair currencyPair;

  Info info;

  @Data
  @Builder
  @Jacksonized
  @JsonFormat(shape = JsonFormat.Shape.ARRAY)
  public static class Info {
    String placeholder1;
    String placeholder2;
    String placeholder3;

    BigDecimal minAssetAmount;

    BigDecimal maxAssetAmount;

    String placeholder4;
    String placeholder5;
    String placeholder6;

    BigDecimal initialMargin;

    BigDecimal minMargin;
  }
}
