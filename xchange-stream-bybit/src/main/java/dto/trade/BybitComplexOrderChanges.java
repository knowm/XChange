package dto.trade;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.trade.BybitOrderType;
import org.knowm.xchange.bybit.dto.trade.BybitSide;
import org.knowm.xchange.bybit.dto.trade.details.BybitTimeInForce;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.instrument.Instrument;

@Getter
@ToString(callSuper = true)
public class BybitComplexOrderChanges extends Order {

  private final BybitCategory category;
  private final String isLeverage;
  private final String blockTradeId;
  private final BigDecimal price;
  private final BigDecimal qty;
  private final BybitSide side;
  private final int positionIdx;
  private final String createType;
  private final String cancelType;
  private final String rejectReason;
  private final BigDecimal leavesQty;
  private final BigDecimal leavesValue;
  private final BigDecimal cumExecValue;
  private final String feeCurrency;
  private final BybitTimeInForce timeInForce;
  private final BybitOrderType orderType;
  private final String stopOrderType;
  private final String ocoTriggerBy;
  private final String orderIv;
  private final String marketUnit;
  private final BigDecimal triggerPrice;
  private final BigDecimal takeProfit;
  private final BigDecimal stopLoss;
  private final String tpslMode;
  private final BigDecimal tpLimitPrice;
  private final BigDecimal slLimitPrice;
  private final String tpTriggerBy;
  private final String slTriggerBy;
  private final int triggerDirection;
  private final String triggerBy;
  private final String lastPriceOnCreated;
  private final boolean reduceOnly;
  private final boolean closeOnTrigger;
  private final String placeType;
  private final String smpType;
  private final int smpGroup;
  private final String smpOrderId;
  private final Date updatedTime;

  public BybitComplexOrderChanges(
      OrderType type,
      BigDecimal originalAmount,
      Instrument instrument,
      String orderLinkId,
      Date createdTime,
      BigDecimal averagePrice,
      BigDecimal cumExecQty,
      BigDecimal cumExecFee,
      OrderStatus orderStatus,
      BybitCategory category,
      String orderId,
      String isLeverage,
      String blockTradeId,
      BigDecimal price,
      BigDecimal qty,
      BybitSide side,
      int positionIdx,
      String createType,
      String cancelType,
      String rejectReason,
      BigDecimal leavesQty,
      BigDecimal leavesValue,
      BigDecimal cumExecValue,
      String feeCurrency,
      BybitTimeInForce timeInForce,
      BybitOrderType orderType,
      String stopOrderType,
      String ocoTriggerBy,
      String orderIv,
      String marketUnit,
      BigDecimal triggerPrice,
      BigDecimal takeProfit,
      BigDecimal stopLoss,
      String tpslMode,
      BigDecimal tpLimitPrice,
      BigDecimal slLimitPrice,
      String tpTriggerBy,
      String slTriggerBy,
      int triggerDirection,
      String triggerBy,
      String lastPriceOnCreated,
      boolean reduceOnly,
      boolean closeOnTrigger,
      String placeType,
      String smpType,
      int smpGroup,
      String smpOrderId,
      Date updatedTime) {
    super(
        type,
        originalAmount,
        instrument,
        orderId,
        createdTime,
        averagePrice,
        cumExecQty,
        cumExecFee,
        orderStatus,
        orderLinkId);
    this.category = category;
    this.isLeverage = isLeverage;
    this.blockTradeId = blockTradeId;
    this.price = price;
    this.qty = qty;
    this.side = side;
    this.positionIdx = positionIdx;
    this.createType = createType;
    this.cancelType = cancelType;
    this.rejectReason = rejectReason;
    this.leavesQty = leavesQty;
    this.leavesValue = leavesValue;
    this.cumExecValue = cumExecValue;
    this.feeCurrency = feeCurrency;
    this.timeInForce = timeInForce;
    this.orderType = orderType;
    this.stopOrderType = stopOrderType;
    this.ocoTriggerBy = ocoTriggerBy;
    this.orderIv = orderIv;
    this.marketUnit = marketUnit;
    this.triggerPrice = triggerPrice;
    this.takeProfit = takeProfit;
    this.stopLoss = stopLoss;
    this.tpslMode = tpslMode;
    this.tpLimitPrice = tpLimitPrice;
    this.slLimitPrice = slLimitPrice;
    this.tpTriggerBy = tpTriggerBy;
    this.slTriggerBy = slTriggerBy;
    this.triggerDirection = triggerDirection;
    this.triggerBy = triggerBy;
    this.lastPriceOnCreated = lastPriceOnCreated;
    this.reduceOnly = reduceOnly;
    this.closeOnTrigger = closeOnTrigger;
    this.placeType = placeType;
    this.smpType = smpType;
    this.smpGroup = smpGroup;
    this.smpOrderId = smpOrderId;
    this.updatedTime = updatedTime;
  }
}
