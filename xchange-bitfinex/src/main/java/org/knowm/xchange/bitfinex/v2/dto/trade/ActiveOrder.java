package org.knowm.xchange.bitfinex.v2.dto.trade;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/** https://docs.bitfinex.com/v2/reference#rest-auth-trades-hist */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@Setter
@Getter
@ToString
public class ActiveOrder {

  /** Order ID */
  private long id;
  /** Group ID */
  private Long gid;
  /** Client Order ID */
  private Long cid;
  /** Pair (tBTCUSD, …) */
  private String symbol;
  /** Millisecond timestamp of creation */
  private long timestampCreate;
  /** Millisecond timestamp of update */
  private Long timestampUpdate;
  /** Positive means buy, negative means sell. */
  private BigDecimal amount;
  /** Original amount. */
  private BigDecimal amountOrig;
  /**
   * The type of the order: LIMIT, MARKET, STOP, TRAILING STOP, EXCHANGE MARKET, EXCHANGE LIMIT,
   * EXCHANGE STOP, EXCHANGE TRAILING STOP, FOK, EXCHANGE FOK, IOC, EXCHANGE IOC.
   */
  private String type;
  /** Previous order type */
  private String typePrev;

  private Object placeHolder0;
  private Object placeHolder1;
  /** Upcoming Params Object (stay tuned) */
  private int flags;
  /**
   * Order Status: ACTIVE, EXECUTED, PARTIALLY FILLED, CANCELED, RSN_DUST (amount is less than
   * 0.00000001), RSN_PAUSE (trading is paused / paused due to AMPL rebase event)
   */
  private String orderStatus;

  private Object placeHolder2;
  private Object placeHolder3;
  /** Price */
  private BigDecimal price;
  /** Average price */
  private BigDecimal priceAvg;
  /** The trailing price */
  private BigDecimal priceTrailing;
  /** Auxiliary Limit price (for STOP LIMIT) */
  private BigDecimal priceAuxLimit;

  private Object placeHolder4;
  private Object placeHolder5;
  private Object placeHolder6;
  /** 1 if Hidden, 0 if not hidden */
  private int hidden;
  /** If another order caused this order to be placed (OCO) this will be that other order's ID */
  private Long placedId;

  public Date getTimestampCreate() {
    return new Date(timestampCreate);
  }
}
