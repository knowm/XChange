package info.bitrich.xchangestream.okex.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Payload for placing an order via OKX WebSocket private channel.
 *
 * <p>Note: OKX API expects strings for most numeric values (price, size, etc.). Optional fields are
 * serialized only when non-null.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OkexWebsocketPlaceOrderPayload {

  /** Instrument ID, e.g. BTC-USDT (required). */
  @JsonProperty("instId")
  private String instId;

  /** Trade mode (required). One of: isolated, cross, cash, spot_isolated. */
  @JsonProperty("tdMode")
  private String tdMode;

  /** Margin currency (optional). Applicable to isolated/cross MARGIN orders in Futures mode. */
  @JsonProperty("ccy")
  private String ccy;

  /** Client Order ID (optional). Up to 32 chars, case-sensitive alphanumerics. */
  @JsonProperty("clOrdId")
  private String clOrdId;

  /** Order tag (optional). Up to 16 chars, case-sensitive alphanumerics. */
  @JsonProperty("tag")
  private String tag;

  /** Order side (required). buy or sell. */
  @JsonProperty("side")
  private String side;

  /**
   * Position side (conditional). net (default), or required long/short in long/short mode.
   * FUTURES/SWAP only.
   */
  @JsonProperty("posSide")
  private String posSide;

  /**
   * Order type (required). market, limit, post_only, fok, ioc, optimal_limit_ioc, mmp,
   * mmp_and_post_only.
   */
  @JsonProperty("ordType")
  private String ordType;

  /** Quantity to buy or sell (required). */
  @JsonProperty("sz")
  private String sz;

  /**
   * Order price (conditional). Applicable to limit/post_only/fok/ioc/mmp/mmp_and_post_only orders.
   */
  @JsonProperty("px")
  private String px;

  /** Price in USD for options (conditional). One of px/pxUsd/pxVol must be set for options. */
  @JsonProperty("pxUsd")
  private String pxUsd;

  /** Implied volatility for options (conditional). 1 represents 100%. One of px/pxUsd/pxVol. */
  @JsonProperty("pxVol")
  private String pxVol;

  /** Reduce-only flag (optional). Only for MARGIN and FUTURES/SWAP in net mode. Default false. */
  @JsonProperty("reduceOnly")
  private Boolean reduceOnly;

  /** Order quantity unit for sz (optional). base_ccy or quote_ccy. Only for SPOT market orders. */
  @JsonProperty("tgtCcy")
  private String tgtCcy;

  /** Disallow system-amend of SPOT Market Order size (optional). Default false. */
  @JsonProperty("banAmend")
  private Boolean banAmend;

  /** Price amendment type for orders (optional). 0 or 1. Default 0. */
  @JsonProperty("pxAmendType")
  private String pxAmendType;

  /** Quote currency used for trading (optional). SPOT only. Defaults to instId quote ccy. */
  @JsonProperty("tradeQuoteCcy")
  private String tradeQuoteCcy;

  /** Self-trade prevention mode (optional). cancel_maker, cancel_taker, cancel_both. */
  @JsonProperty("stpMode")
  private String stpMode;

  /** Request effective deadline in ms (optional). Unix timestamp in milliseconds. */
  @JsonProperty("expTime")
  private String expTime;
}
