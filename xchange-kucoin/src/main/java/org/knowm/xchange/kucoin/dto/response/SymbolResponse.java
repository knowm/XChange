/** Copyright 2019 Mek Global Limited. */
package org.knowm.xchange.kucoin.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import lombok.Data;

/** Created by devin@kucoin.com on 2018-12-27. */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SymbolResponse {

  private String symbol;

  private String name;

  private String market;

  private String baseCurrency;

  private String quoteCurrency;

  private BigDecimal baseMinSize;

  private BigDecimal quoteMinSize;

  private BigDecimal baseMaxSize;

  private BigDecimal quoteMaxSize;

  private BigDecimal baseIncrement;

  private BigDecimal quoteIncrement;

  private BigDecimal priceIncrement;

  private boolean enableTrading;

  private String feeCurrency;

  private boolean isMarginEnabled;

  private BigDecimal priceLimitRate;
}
