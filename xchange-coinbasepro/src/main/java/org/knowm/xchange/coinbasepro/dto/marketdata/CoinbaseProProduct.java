package org.knowm.xchange.coinbasepro.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CoinbaseProProduct {

  private final String id;
  private final String baseCurrency;
  private final String targetCurrency;
  private final BigDecimal minMarketFunds;
  private final BigDecimal baseIncrement;
  private final BigDecimal quoteIncrement;
  private final boolean cancelOnly;
  private final boolean limitOnly;
  private final boolean postOnly;
  private final boolean tradingDisabled;
  private final boolean fxStablecoin;
  private final String status;

  public CoinbaseProProduct(
      @JsonProperty("id") String id,
      @JsonProperty("base_currency") String baseCurrency,
      @JsonProperty("quote_currency") String targetCurrency,
      @JsonProperty("min_market_funds") BigDecimal minMarketFunds,
      @JsonProperty("base_increment") BigDecimal baseIncrement,
      @JsonProperty("quote_increment") BigDecimal quoteIncrement,
      @JsonProperty("cancel_only") boolean cancelOnly,
      @JsonProperty("limit_only") boolean limitOnly,
      @JsonProperty("post_only") boolean postOnly,
      @JsonProperty("trading_disabled") boolean tradingDisabled,
      @JsonProperty("fx_stablecoin") boolean fxStablecoin,
      @JsonProperty("status") String status) {

    this.id = id;
    this.baseCurrency = baseCurrency;
    this.targetCurrency = targetCurrency;
    this.minMarketFunds = minMarketFunds;
    this.baseIncrement = baseIncrement;
    this.quoteIncrement = quoteIncrement;
    this.cancelOnly = cancelOnly;
    this.limitOnly = limitOnly;
    this.postOnly = postOnly;
    this.tradingDisabled = tradingDisabled;
    this.fxStablecoin = fxStablecoin;
    this.status = status;
  }
}
