package org.knowm.xchange.gateio.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GateioFuturesTicker {

  /**
   * The latest transaction price.
   */
  @JsonProperty("last")
  private String last;

  /**
   * The percentage change, with negative values indicating a decline (e.g., -7.45).
   */
  @JsonProperty("change_percentage")
  private String changePercentage;

  /**
   * The current total open interest for the contract.
   */
  @JsonProperty("total_size")
  private String totalSize;

  /**
   * The lowest price in the last 24 hours.
   */
  @JsonProperty("low_24h")
  private String low24h;

  /**
   * The highest price in the last 24 hours.
   */
  @JsonProperty("high_24h")
  private String high24h;

  /**
   * The total trading volume in the last 24 hours.
   */
  @JsonProperty("volume_24h")
  private String volume24h;

  /**
   * The total trading volume in BTC in the last 24 hours (deprecated, use volume_24h_base,
   * volume_24h_quote, or volume_24h_settle).
   */
  @JsonProperty("volume_24h_btc")
  private String volume24hBtc;

  /**
   * The total trading volume in USD in the last 24 hours (deprecated, use volume_24h_base,
   * volume_24h_quote, or volume_24h_settle).
   */
  @JsonProperty("volume_24h_usd")
  private String volume24hUsd;

  /**
   * The total trading volume in the base currency over the last 24 hours.
   */
  @JsonProperty("volume_24h_base")
  private String volume24hBase;

  /**
   * The total trading volume in the quote currency over the last 24 hours.
   */
  @JsonProperty("volume_24h_quote")
  private String volume24hQuote;

  /**
   * The total trading volume in the settlement currency over the last 24 hours.
   */
  @JsonProperty("volume_24h_settle")
  private String volume24hSettle;

  /**
   * The most recent mark price.
   */
  @JsonProperty("mark_price")
  private String markPrice;

  /**
   * The current funding rate.
   */
  @JsonProperty("funding_rate")
  private String fundingRate;

  /**
   * The predicted funding rate for the next period.
   */
  @JsonProperty("funding_rate_indicative")
  private String fundingRateIndicative;

  /**
   * The index price.
   */
  @JsonProperty("index_price")
  private String indexPrice;

  /**
   * The exchange rate between the base and settlement currencies in quanto contracts. Not
   * applicable to other contract types.
   */
  @JsonProperty("quanto_base_rate")
  private String quantoBaseRate;

  /**
   * The basis rate.
   */
  @JsonProperty("basis_rate")
  private String basisRate;

  /**
   * The basis value.
   */
  @JsonProperty("basis_value")
  private String basisValue;

  /**
   * The latest lowest ask price.
   */
  @JsonProperty("lowest_ask")
  private String lowestAsk;

  /**
   * The order size at the latest lowest ask price.
   */
  @JsonProperty("lowest_size")
  private String lowestSize;

  /**
   * The latest highest bid price.
   */
  @JsonProperty("highest_bid")
  private String highestBid;

  /**
   * The order size at the latest highest bid price.
   */
  @JsonProperty("highest_size")
  private String highestSize;
}
