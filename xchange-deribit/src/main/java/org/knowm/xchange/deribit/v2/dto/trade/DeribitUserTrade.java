package org.knowm.xchange.deribit.v2.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.deribit.v2.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.deribit.v2.config.converter.StringToOrderTypeConverter;
import org.knowm.xchange.dto.Order;

@Data
@Builder
@Jacksonized
public class DeribitUserTrade {

  /**
   * Trade amount. For perpetual and futures - in USD units, for options it is amount of
   * corresponding cryptocurrency contracts, e.g., BTC or ETH.
   */
  private BigDecimal amount;

  /** direction, buy or sell */
  @JsonProperty("direction")
  @JsonDeserialize(converter = StringToOrderTypeConverter.class)
  private Order.OrderType orderSide;


  /** User's fee in units of the specified fee_currency */
  private BigDecimal fee;

  /** Currency, i.e "BTC", "ETH" */
  @JsonProperty("fee_currency")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency feeCurrency;

  /** Index Price at the moment of trade */
  @JsonProperty("index_price")
  private BigDecimal indexPrice;

  /** Unique instrument identifier */
  @JsonProperty("instrument_name")
  private String instrumentName;

  /** Option implied volatility for the price (Option only) */
  @JsonProperty("iv")
  private BigDecimal iv;

  /** User defined label (presented only when previously set for order by user) */
  private String label;

  /**
   * Optional field (only for trades caused by liquidation): "M" when maker side of trade was under
   * liquidation, "T" when taker side was under liquidation, "MT" when both sides of trade were
   * under liquidation
   */
  private String liquidation;

  /**
   * Describes what was role of users order: "M" when it was maker order, "T" when it was taker
   * order
   */
  private String liquidity;

  /**
   * Always null, except for a self-trade which is possible only if self-trading is switched on for
   * the account (in that case this will be id of the maker order of the subscriber)
   */
  @JsonProperty("matching_id")
  private String matchingId;

  /**
   * Id of the user order (maker or taker), i.e. subscriber's order id that took part in the trade
   */
  @JsonProperty("order_id")
  private String orderId;

  /** Order type: "limit, "market", or "liquidation" */
  @JsonProperty("order_type")
  private String orderType;

  /** Price in base currency */
  private BigDecimal price;

  @JsonProperty("self_trade")
  /**
   * true if the trade is against own order. This can only happen when your account has self-trading
   * enabled. Contact an administrator if you think you need that
   */
  private boolean selfTrade;

  /**
   * order state, "open", "filled", "rejected", "cancelled", "untriggered" or "archive" (if order
   * was archived)
   */
  private OrderState state;

  /**
   * Direction of the "tick" (0 = Plus Tick, 1 = Zero-Plus Tick, 2 = Minus Tick, 3 = Zero-Minus
   * Tick).
   */
  @JsonProperty("tick_direction")
  private Integer tickDirection;

  /** The timestamp of the trade */
  @JsonProperty("timestamp")
  private Instant timestamp;

  /** Unique (per currency) trade identifier */
  @JsonProperty("trade_id")
  private String tradeId;

  /** The sequence number of the trade within instrument */
  @JsonProperty("trade_seq")
  private long tradeSeq;

}
