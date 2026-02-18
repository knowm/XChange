/** Copyright 2019 Mek Global Limited. */
package org.knowm.xchange.kucoin.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.kucoin.config.converter.StringToCurrencyConverter;

@Data
@AllArgsConstructor
public class SymbolResponse {

  private String symbol;

  private CurrencyPair currencyPair;

  private String market;

  private Currency baseCurrency;

  private Currency quoteCurrency;

  private BigDecimal baseMinSize;

  private BigDecimal quoteMinSize;

  private BigDecimal baseMaxSize;

  private BigDecimal quoteMaxSize;

  private BigDecimal baseIncrement;

  private BigDecimal quoteIncrement;

  private BigDecimal priceIncrement;

  private boolean enableTrading;

  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency feeCurrency;

  private boolean isMarginEnabled;

  private BigDecimal priceLimitRate;

  private BigDecimal minFunds;

  @JsonCreator
  public SymbolResponse(
      @JsonProperty("baseCurrency") String baseCurrency,
      @JsonProperty("quoteCurrency") String quoteCurrency) {
    this.baseCurrency = Currency.getInstance(baseCurrency);
    this.quoteCurrency = Currency.getInstance(quoteCurrency);
    this.currencyPair = new CurrencyPair(this.baseCurrency, this.quoteCurrency);
  }
}
