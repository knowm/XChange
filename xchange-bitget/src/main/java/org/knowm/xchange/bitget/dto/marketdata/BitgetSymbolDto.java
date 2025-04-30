package org.knowm.xchange.bitget.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitget.config.converter.StringToBooleanConverter;
import org.knowm.xchange.bitget.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

@Data
@Builder
@Jacksonized
public class BitgetSymbolDto {

  @JsonProperty("symbol")
  private String symbol;

  @JsonProperty("baseCoin")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency base;

  @JsonProperty("quoteCoin")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency quote;

  @JsonProperty("minTradeAmount")
  private BigDecimal minTradeAmount;

  @JsonProperty("maxTradeAmount")
  private BigDecimal maxTradeAmount;

  @JsonProperty("takerFeeRate")
  private BigDecimal takerFeeRate;

  @JsonProperty("makerFeeRate")
  private BigDecimal makerFeeRate;

  @JsonProperty("pricePrecision")
  private Integer pricePrecision;

  @JsonProperty("quantityPrecision")
  private Integer quantityPrecision;

  @JsonProperty("quotePrecision")
  private Integer quotePrecision;

  @JsonProperty("status")
  private Status status;

  @JsonProperty("minTradeUSDT")
  private BigDecimal minTradeUSDT;

  @JsonProperty("buyLimitPriceRatio")
  private BigDecimal buyLimitPriceRatio;

  @JsonProperty("sellLimitPriceRatio")
  private BigDecimal sellLimitPriceRatio;

  @JsonProperty("areaSymbol")
  @JsonDeserialize(converter = StringToBooleanConverter.class)
  private Boolean isAreaSymbol;

  public CurrencyPair getCurrencyPair() {
    return new CurrencyPair(base, quote);
  }

  public static enum Status {
    @JsonProperty("offline")
    OFFLINE,

    @JsonProperty("gray")
    GRAY,

    @JsonProperty("online")
    ONLINE,

    @JsonProperty("halt")
    HALT
  }
}
