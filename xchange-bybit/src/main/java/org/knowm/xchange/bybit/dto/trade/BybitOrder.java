package org.knowm.xchange.bybit.dto.trade;

import com.fasterxml.jackson.annotation.JsonValue;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.bybit.dto.trade.details.BybitTimeInForce;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.instrument.Instrument;

@Getter
@Setter
public class BybitOrder extends Order {

  private BigDecimal sLTriggerPrice;
  private SlTriggerBy slTriggerBy;
  private BigDecimal slLimitPrice;
  private BybitOrderType slOrderType;
  private BybitOrderType orderType;
  private boolean reduceOnly = false;

  /**
   * 0: one-way mode
   *
   * <p>1: hedge-mode Buy side
   *
   * <p>2: hedge-mode Sell side
   */
  private int positionIdx;

  private BybitTimeInForce timeInForce;

  public BybitOrder(
      OrderType type,
      BybitOrderType orderType,
      String userId,
      Instrument instrument,
      BigDecimal amount,
      BigDecimal price,
      Date timestamp,
      BigDecimal sLTriggerPrice,
      SlTriggerBy slTriggerBy,
      BigDecimal slLimitPrice,
      BybitOrderType slOrderType,
      BigDecimal fee,
      BybitTimeInForce timeInForce,
      boolean reduceOnly,
      int positionIdx) {
    super(
        type,
        amount,
        instrument,
        "",
        timestamp,
        price,
        amount,
        fee,
        OrderStatus.PENDING_NEW,
        userId);
    this.orderType = orderType;
    this.sLTriggerPrice = sLTriggerPrice;
    this.slTriggerBy = slTriggerBy;
    this.slLimitPrice = slLimitPrice;
    this.slOrderType = slOrderType;
    this.timeInForce = timeInForce;
    this.reduceOnly = reduceOnly;
    this.positionIdx = positionIdx;
  }

  public BybitOrder(
      OrderType type,
      BybitOrderType orderType,
      String userId,
      Instrument instrument,
      BigDecimal amount,
      BigDecimal price,
      Date timestamp) {
    super(
        type,
        amount,
        instrument,
        "",
        timestamp,
        price,
        amount,
        null,
        OrderStatus.PENDING_NEW,
        userId);
    this.orderType = orderType;
  }

  @Getter
  @AllArgsConstructor
  public enum SlTriggerBy {
    LASTPRICE("LastPrice"),
    MARKPRICE("MarkPrice"),
    INDEXPRICE("IndexPrice");
    @JsonValue private final String value;
  }

  @Getter
  @AllArgsConstructor
  public enum TpslMode {
    FULL("Full"),
    PARTIAL("Partial");
    @JsonValue private final String value;
  }
}
