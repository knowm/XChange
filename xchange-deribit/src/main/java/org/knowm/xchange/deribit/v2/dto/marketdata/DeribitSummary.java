package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DeribitSummary {

  /** Volume in usd (futures only) */
  @JsonProperty("volume_usd")
  private BigDecimal volumeUsd;

  /** The total 24h traded volume (in base currency) */
  @JsonProperty("volume")
  private BigDecimal volume;

  /** underlying price for implied volatility calculations (options only) */
  @JsonProperty("underlying_price")
  private BigDecimal underlyingPrice;

  /** Name of the underlying future, or 'index_price' (options only) */
  @JsonProperty("underlying_index")
  private String underlyingIndex;

  /** Quote currency */
  @JsonProperty("quote_currency")
  private String quoteCurrency;

  /**
   * The total amount of outstanding contracts in the corresponding amount units. For perpetual and
   * futures the amount is in USD units, for options it is amount of corresponding cryptocurrency
   * contracts, e.g., BTC or ETH.
   */
  @JsonProperty("open_interest")
  private BigDecimal openInterest;

  /** The average of the best bid and ask, null if there aren't any asks or bids */
  @JsonProperty("mid_price")
  private BigDecimal midPrice;

  /** The current instrument market price */
  @JsonProperty("mark_price")
  private BigDecimal markPrice;

  /** Price of the 24h lowest trade, null if there weren't any trades */
  @JsonProperty("low")
  private BigDecimal low;

  /** The price of the latest trade, null if there weren't any trades */
  @JsonProperty("last")
  private BigDecimal last;

  /** Interest rate used in implied volatility calculations (options only) */
  @JsonProperty("interest_rate")
  private BigDecimal interestRate;

  /** Unique instrument identifier */
  @JsonProperty("instrument_name")
  private String instrumentName;

  /** Price of the 24h highest trade */
  @JsonProperty("high")
  private BigDecimal high;

  /** Funding 8h (perpetual only) */
  @JsonProperty("funding_8h")
  private BigDecimal funding8h;

  /**
   * Estimated delivery price, in USD. For more details, see Documentation > General > Expiration
   * Price
   */
  @JsonProperty("estimated_delivery_price")
  private BigDecimal estimatedDeliveryPrice;

  /** Current funding (perpetual only) */
  @JsonProperty("current_funding")
  private BigDecimal currentFunding;

  /** The timestamp (seconds since the Unix epoch, with millisecond precision) */
  @JsonProperty("creation_timestamp")
  private long creationTimestamp;

  /** The current best bid price, null if there aren't any bids */
  @JsonProperty("bid_price")
  private BigDecimal bidPrice;

  /** Base currency */
  @JsonProperty("base_currency")
  private String baseCurrency;

  /** The current best ask price, null if there aren't any asks */
  @JsonProperty("ask_price")
  private BigDecimal askPrice;

  public Date getCreationTimestamp() {
    return new Date(creationTimestamp);
  }
}
