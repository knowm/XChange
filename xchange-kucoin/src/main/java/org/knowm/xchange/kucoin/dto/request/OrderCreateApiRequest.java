package org.knowm.xchange.kucoin.dto.request;

import lombok.Builder;
import lombok.Getter;

/** Represents a request to create a new order (limit, market, or stop) on KuCoin. */
@Getter
@Builder
public class OrderCreateApiRequest {

  /** Unique order id created by users to identify their orders, e.g. UUID. */
  private final String clientOid;

  /** A valid trading symbol code, e.g. ETH-BTC. */
  private final String symbol;

  /** The type of trading, e.g. TRADE (Spot Trade). Default is TRADE. */
  @Builder.Default private final String tradeType = "TRADE";

  /** buy or sell. */
  private final String side;

  /** limit or market (default is limit). */
  @Builder.Default private final String type = "limit";

  /** Remark for the order, length cannot exceed 100 utf8 characters. */
  private final String remark;

  /** Self-trade prevention strategy: CN, CO, CB or DC. */
  private final String stp;

  // Stop-related fields (optional)

  /** "entry" or "loss" (KuCoin’s stop types). */
  private final String stop;

  /** Trigger price for the stop order. Null for regular orders. */
  private final String stopPrice;

  // Common fields

  /** [limit order] Price per base currency. */
  private final String price;

  /** [limit order] Amount of base currency to buy or sell. */
  private final String size;

  /** [limit order] Time in force strategy: GTC, GTT, IOC, or FOK (default is GTC). */
  @Builder.Default private final String timeInForce = "GTC";

  /** [limit order] Cancel after n seconds, requires timeInForce = GTT. */
  private final Long cancelAfter;

  /** [limit order] Post only flag. */
  private final boolean postOnly;

  /** [limit order] Order will not be displayed in the order book. */
  private final boolean hidden;

  /** [limit order] Only a portion of the order is displayed in the order book. */
  private final boolean iceberg;

  /** [limit order] The maximum visible size of an iceberg order. */
  private final String visibleSize;

  /**
   * [market order] The amount of quote currency to spend. size and funds are mutually exclusive.
   */
  private final String funds;
}
