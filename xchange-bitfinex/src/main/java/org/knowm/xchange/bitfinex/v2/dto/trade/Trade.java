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
public class Trade {

  /** Trade database id */
  private String id;
  /** Pair (BTCUSD, …) */
  private String symbol;
  /** Execution timestamp millis */
  private long timestamp;
  /** Order id */
  private String orderId;
  /** Positive means buy, negative means sell */
  private BigDecimal execAmount;
  /** Execution price */
  private BigDecimal execPrice;
  /** Order type */
  private String orderType;
  /** Order price */
  private BigDecimal orderPrice;
  /** 1 if true, -1 if false */
  private int maker;
  /** Fee */
  private BigDecimal fee;
  /** Fee currency */
  private String feeCurrency;

  public Date getTimestamp() {
    return new Date(timestamp);
  }
}
