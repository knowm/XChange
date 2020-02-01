package org.knowm.xchange.bitfinex.v2.dto.trade;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/** https://docs.bitfinex.com/v2/reference#rest-auth-positions */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@Setter
@Getter
@ToString
public class Position {

  /** Pair (tBTCUSD, …). */
  private String symbol;
  /** Status (ACTIVE, CLOSED). */
  private String status;
  /**
   * ±amount Size of the position. Positive values means a long position, negative values means a
   * short position.
   */
  private BigDecimal amount;
  /** The price at which you entered your position. */
  private BigDecimal basePrice;
  /** The amount of funding being used for this position. */
  private BigDecimal marginFunding;
  /** 0 for daily, 1 for term. */
  private int marginFundingType;
  /** Profit & Loss */
  private BigDecimal pl;
  /** Profit & Loss Percentage */
  private BigDecimal plPercent;
  /** Liquidation price */
  private BigDecimal priceLiq;
  /** Beta value */
  private BigDecimal leverage;

  private Object placeHolder0;
  /** Position ID */
  private Integer positionId;
  /** Millisecond timestamp of creation */
  private long timestampCreate;
  /** Millisecond timestamp of update */
  private Long timestampUpdate;

  private Object placeHolder1;
  /** Identifies the type of position, 0 = Margin position, 1 = Derivatives position */
  private Integer type;

  private Object placeHolder2;
  /** The amount of collateral applied to the open position */
  private BigDecimal collateral;
  /** The minimum amount of collateral required for the position */
  private BigDecimal collateralMin;
  /** Additional meta information about the position */
  private Object meta;
}
