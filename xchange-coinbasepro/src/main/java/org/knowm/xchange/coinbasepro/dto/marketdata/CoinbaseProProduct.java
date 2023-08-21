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
  private final String quoteCurrency;
  private final BigDecimal quoteIncrement;
  private final BigDecimal baseIncrement;
  private final String displayName;
  private final BigDecimal minMarketFunds;
  private final boolean marginEnabled;
  private final boolean postOnly;
  private final boolean limitOnly;
  private final boolean cancelOnly;
  private final CoinbaseProProductStatus status;
  private final String statusMessage;
  private final boolean tradingDisabled;
  private final boolean fxStablecoin;
  private final BigDecimal maxSlippagePercentage;
  private final boolean auctionMode;
  private final BigDecimal highBidLimitPercentage;

  public CoinbaseProProduct(
      @JsonProperty("id") String id,
      @JsonProperty("base_currency") String baseCurrency,
      @JsonProperty("quote_currency") String quoteCurrency,
      @JsonProperty("quote_increment") BigDecimal quoteIncrement,
      @JsonProperty("base_increment") BigDecimal baseIncrement,
      @JsonProperty("display_name") String displayName,
      @JsonProperty("min_market_funds") BigDecimal minMarketFunds,
      @JsonProperty("margin_enabled") boolean marginEnabled,
      @JsonProperty("post_only") boolean postOnly,
      @JsonProperty("limit_only") boolean limitOnly,
      @JsonProperty("cancel_only") boolean cancelOnly,
      @JsonProperty("status") CoinbaseProProductStatus status,
      @JsonProperty("status_message") String statusMessage,
      @JsonProperty("trading_disabled") boolean tradingDisabled,
      @JsonProperty("fx_stablecoin") boolean fxStablecoin,
      @JsonProperty("max_slippage_percentage") BigDecimal maxSlippagePercentage,
      @JsonProperty("auction_mode") boolean auctionMode,
      @JsonProperty("high_bid_limit_percentage") BigDecimal highBidLimitPercentage) {
    this.id = id;
    this.baseCurrency = baseCurrency;
    this.quoteCurrency = quoteCurrency;
    this.quoteIncrement = quoteIncrement;
    this.baseIncrement = baseIncrement;
    this.displayName = displayName;
    this.minMarketFunds = minMarketFunds;
    this.marginEnabled = marginEnabled;
    this.postOnly = postOnly;
    this.limitOnly = limitOnly;
    this.cancelOnly = cancelOnly;
    this.status = status;
    this.statusMessage = statusMessage;
    this.tradingDisabled = tradingDisabled;
    this.fxStablecoin = fxStablecoin;
    this.maxSlippagePercentage = maxSlippagePercentage;
    this.auctionMode = auctionMode;
    this.highBidLimitPercentage = highBidLimitPercentage;
  }

  public enum CoinbaseProProductStatus {
    online,
    offline,
    internal,
    delisted
  }
}
