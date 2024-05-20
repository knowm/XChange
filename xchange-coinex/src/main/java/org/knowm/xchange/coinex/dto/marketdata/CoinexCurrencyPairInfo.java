package org.knowm.xchange.coinex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.coinex.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.currency.Currency;

@Data
@Builder
@Jacksonized
public class CoinexCurrencyPairInfo {

  @JsonProperty("base_ccy")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency baseCurrency;

  @JsonProperty("quote_ccy")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency quoteCurrency;

  @JsonProperty("base_ccy_precision")
  private Integer baseCurrencyPrecision;

  @JsonProperty("quote_ccy_precision")
  private Integer quoteCurrencyPrecision;

  @JsonProperty("is_amm_available")
  private Boolean ammAvailable;

  @JsonProperty("is_margin_available")
  private Boolean marginAvailable;

  @JsonProperty("maker_fee_rate")
  private BigDecimal makerFeeRate;

  @JsonProperty("taker_fee_rate")
  private BigDecimal takerFeeRate;

  @JsonProperty("market")
  private String symbol;

  @JsonProperty("min_amount")
  private BigDecimal minAssetAmount;

}
