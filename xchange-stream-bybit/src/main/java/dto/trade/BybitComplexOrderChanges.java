package dto.trade;

import com.fasterxml.jackson.annotation.JsonValue;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.trade.BybitOrderType;
import org.knowm.xchange.bybit.dto.trade.BybitSide;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.instrument.Instrument;

@Getter
@Setter
public class BybitComplexOrderChanges extends Order {
  private BybitCategory category;
  private BigDecimal price;
  private BybitSide side;
  private BigDecimal leavesQty;
  private BigDecimal leavesValue;
  private BigDecimal cumExecValue;
  private String feeCurrency;
  private TimeInForce timeInForce;
  private BybitOrderType orderType;
  private boolean reduceOnly;
  private Date updatedTime;

  public BybitComplexOrderChanges(
      OrderType type,
      BigDecimal originalAmount,
      Instrument instrument,
      String id,
      Date timestamp,
      BigDecimal averagePrice,
      BigDecimal cumulativeAmount,
      BigDecimal fee,
      OrderStatus status,
      String userReference) {
    super(
        type,
        originalAmount,
        instrument,
        id,
        timestamp,
        averagePrice,
        cumulativeAmount,
        fee,
        status,
        userReference);
  }

  public BybitComplexOrderChanges(
      OrderType type,
      BigDecimal originalAmount,
      Instrument instrument,
      String id,
      Date timestamp,
      BigDecimal averagePrice,
      BigDecimal cumulativeAmount,
      BigDecimal fee,
      OrderStatus status,
      String userReference,
      BybitCategory category,
      BigDecimal price,
      BybitSide side,
      BigDecimal leavesQty,
      BigDecimal leavesValue,
      BigDecimal cumExecValue,
      String feeCurrency,
      TimeInForce timeInForce,
      BybitOrderType orderType,
      boolean reduceOnly,
      Date updatedTime) {
    super(
        type,
        originalAmount,
        instrument,
        id,
        timestamp,
        averagePrice,
        cumulativeAmount,
        fee,
        status,
        userReference);
    this.category = category;
    this.price = price;
    this.side = side;
    this.leavesQty = leavesQty;
    this.leavesValue = leavesValue;
    this.cumExecValue = cumExecValue;
    this.feeCurrency = feeCurrency;
    this.timeInForce = timeInForce;
    this.orderType = orderType;
    this.reduceOnly = reduceOnly;
    this.updatedTime = updatedTime;
  }

  @Getter
  @AllArgsConstructor
  public enum TimeInForce {
    GTC("GTC"),
    IOC("IOC"),
    FOK("FOK"),
    POSTONLY("PostOnly");
    @JsonValue private final String value;
  }
}
