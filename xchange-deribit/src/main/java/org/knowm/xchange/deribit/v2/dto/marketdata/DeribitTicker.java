package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DeribitTicker {

  /** (Only for option) implied volatility for best ask */
  @JsonProperty("ask_iv")
  private BigDecimal askIv;

  /** It represents the requested order size of all best asks */
  @JsonProperty("best_ask_amount")
  private BigDecimal bestAskAmount;

  /** The current best ask price, null if there aren't any asks */
  @JsonProperty("best_ask_price")
  private BigDecimal bestAskPrice;

  /** It represents the requested order size of all best bids */
  @JsonProperty("best_bid_amount")
  private BigDecimal bestBidAmount;

  /** The current best bid price, null if there aren't any bids */
  @JsonProperty("best_bid_price")
  private BigDecimal bestBidPrice;

  /** (Only for option) implied volatility for best bid */
  @JsonProperty("bid_iv")
  private BigDecimal bidIv;

  @JsonProperty("greeks")
  private DeribitGreeks greeks;

  /** The settlement price for the instrument. Only when state = closed */
  @JsonProperty("delivery_price")
  private BigDecimal deliveryPrice;

  /** Current funding (perpetual only) */
  @JsonProperty("current_funding")
  private BigDecimal currentFunding;

  /** Funding 8h (perpetual only) */
  @JsonProperty("funding_8h")
  private BigDecimal funding8h;

  /** Current index price */
  @JsonProperty("index_price")
  private BigDecimal indexPrice;

  /** Unique instrument identifier */
  @JsonProperty("instrument_name")
  private String instrumentName;

  /** Interest rate used in implied volatility calculations (options only) */
  @JsonProperty("interest_rate")
  private BigDecimal interestRate;

  /** The price for the last trade */
  @JsonProperty("last_price")
  private BigDecimal lastPrice;

  /** (Only for option) implied volatility for mark price */
  @JsonProperty("mark_iv")
  private BigDecimal markIv;

  /** The mark price for the instrument */
  @JsonProperty("mark_price")
  private BigDecimal markPrice;

  /**
   * The maximum price for the future. Any buy orders you submit higher than this price, will be
   * clamped to this maximum.
   */
  @JsonProperty("max_price")
  private BigDecimal maxPrice;

  /**
   * The minimum price for the future. Any sell orders you submit lower than this price will be
   * clamped to this minimum.
   */
  @JsonProperty("min_price")
  private BigDecimal minPrice;

  /**
   * The total amount of outstanding contracts in the corresponding amount units. For perpetual and
   * futures the amount is in USD units, for options it is amount of corresponding cryptocurrency
   * contracts, e.g., BTC or ETH.
   */
  @JsonProperty("open_interest")
  private BigDecimal openInterest;

  /** The settlement price for the instrument. Only when state = open */
  @JsonProperty("settlement_price")
  private BigDecimal settlementPrice;

  /** The state of the order book. Possible values are open and closed. */
  @JsonProperty("state")
  private String state;

  @JsonProperty("stats")
  private DeribitStats stats;

  /** The timestamp (seconds since the Unix epoch, with millisecond precision) */
  @JsonProperty("timestamp")
  private long timestamp;

  /** Name of the underlying future, or index_price (options only) */
  @JsonProperty("underlying_index")
  private String underlyingIndex;

  /** Underlying price for implied volatility calculations (options only) */
  @JsonProperty("underlying_price")
  private BigDecimal underlyingPrice;

  public Date getTimestamp() {
    return new Date(timestamp);
  }
}
