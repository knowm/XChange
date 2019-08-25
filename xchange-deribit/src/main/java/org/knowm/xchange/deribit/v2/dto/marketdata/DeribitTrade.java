package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import org.knowm.xchange.deribit.v2.dto.Direction;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DeribitTrade {

  /** The sequence number of the trade within instrument */
  @JsonProperty("trade_seq")
  private long tradeSeq;

  /** Unique (per currency) trade identifier */
  @JsonProperty("trade_id")
  private String tradeId;

  /** The timestamp of the trade */
  @JsonProperty("timestamp")
  private long timestamp;

  /**
   * Direction of the "tick" (0 = Plus Tick, 1 = Zero-Plus Tick, 2 = Minus Tick, 3 = Zero-Minus
   * Tick).
   */
  @JsonProperty("tick_direction")
  private int tickDirection;

  /** Price in base currency */
  @JsonProperty("price")
  private BigDecimal price;

  /**
   * Optional field (only for trades caused by liquidation): "M" when maker side of trade was under
   * liquidation, "T" when taker side was under liquidation, "MT" when both sides of trade were
   * under liquidation
   */
  @JsonProperty("liquidation")
  private String liquidation;

  /** Option implied volatility for the price (Option only) */
  @JsonProperty("iv")
  private BigDecimal iv;

  /** Unique instrument identifier */
  @JsonProperty("instrument_name")
  private String instrumentName;

  /** Index Price at the moment of trade */
  @JsonProperty("index_price")
  private BigDecimal indexPrice;

  /** direction, buy or sell */
  @JsonProperty("direction")
  private Direction direction;

  /**
   * Trade amount. For perpetual and futures - in USD units, for options it is amount of
   * corresponding cryptocurrency contracts, e.g., BTC or ETH.
   */
  @JsonProperty("amount")
  private BigDecimal amount;

  public Date getTimestamp() {
    return new Date(timestamp);
  }
}
