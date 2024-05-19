package org.knowm.xchange.bybit.dto.trade;

import com.fasterxml.jackson.annotation.JsonValue;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.instrument.Instrument;

@Getter
@Setter
public class BybitAdvancedOrder extends Order {

  private BigDecimal sLTriggerPrice;
  private SlTriggerBy slTriggerBy;
  private BigDecimal slLimitPrice;
  private BybitOrderType slOrderType;
  private BybitOrderType orderType;
  private boolean reduceOnly = false;
  private TimeInForce timeInForce;

  public BybitAdvancedOrder(OrderType type, BybitOrderType orderType, String userId,  Instrument instrument,
      BigDecimal amount, BigDecimal price, Date timestamp, BigDecimal sLTriggerPrice, SlTriggerBy slTriggerBy, BigDecimal slLimitPrice,
      BybitOrderType slOrderType, BigDecimal fee, TimeInForce timeInForce, boolean reduceOnly) {
    super(type, amount, instrument, "", timestamp, price, amount, fee,
        OrderStatus.PENDING_NEW, userId);
    this.orderType = orderType;
    this.sLTriggerPrice = sLTriggerPrice;
    this.slTriggerBy = slTriggerBy;
    this.slLimitPrice = slLimitPrice;
    this.slOrderType = slOrderType;
    this.timeInForce = timeInForce;
    this.reduceOnly = reduceOnly;
  }

  public BybitAdvancedOrder(OrderType type, BybitOrderType orderType, String userId, Instrument instrument,
      BigDecimal amount, BigDecimal price, Date timestamp) {
    super(type, amount, instrument, "", timestamp, price, amount, null,
        OrderStatus.PENDING_NEW, userId);
    this.orderType = orderType;
  }

  @Getter
  @AllArgsConstructor
  public enum SlTriggerBy {
    LASTPRICE("LastPrice"),
    MARKPRICE("MarkPrice"),
    INDEXPRICE("IndexPrice");
    @JsonValue
    private final String value;
  }

  @Getter
  @AllArgsConstructor
  public enum TpslMode {
    FULL("Full"),
    PARTIAL("Partial");
    @JsonValue
    private final String value;
  }

  @Getter
  @AllArgsConstructor
  public enum TimeInForce {
    GTC("GTC"),
    IOC("IOC"),
    FOK("FOK"),
    POSTONLY("PostOnly");
    @JsonValue
    private final String value;
  }

}
