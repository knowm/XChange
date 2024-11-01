package dto.trade;

import com.fasterxml.jackson.annotation.JsonValue;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.trade.BybitOrderType;
import org.knowm.xchange.bybit.dto.trade.BybitSide;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.instrument.Instrument;

@Getter
@ToString(callSuper = true)
public class BybitComplexOrderChanges extends Order {

  private BybitCategory category;
  private String isLeverage;
  private String blockTradeId;
  private BigDecimal price;
  private BigDecimal qty;
  private BybitSide side;
  private int positionIdx;
  private String createType;
  private String cancelType;
  private String rejectReason;
  private BigDecimal leavesQty;
  private BigDecimal leavesValue;
  private BigDecimal cumExecQty;
  private BigDecimal cumExecValue;
  private BigDecimal cumExecFee;
  private String feeCurrency;
  private TimeInForce timeInForce;
  private BybitOrderType orderType;
  private String stopOrderType;
  private String ocoTriggerBy;
  private String orderIv;
  private String marketUnit;
  private BigDecimal triggerPrice;
  private BigDecimal takeProfit;
  private BigDecimal stopLoss;
  private String tpslMode;
  private BigDecimal tpLimitPrice;
  private BigDecimal slLimitPrice;
  private String tpTriggerBy;
  private String slTriggerBy;
  private int triggerDirection;
  private String triggerBy;
  private String lastPriceOnCreated;
  private boolean reduceOnly;
  private boolean closeOnTrigger;
  private String placeType;
  private String smpType;
  private int smpGroup;
  private String smpOrderId;
  private Date updatedTime;

  public BybitComplexOrderChanges(OrderType type, BigDecimal originalAmount, Instrument instrument,
      String orderLinkId, Date createdTime, BigDecimal averagePrice, BigDecimal cumulativeAmount,
      BigDecimal fee, OrderStatus orderStatus, BybitCategory category, String orderId,
      String isLeverage, String blockTradeId, BigDecimal price,
      BigDecimal qty, BybitSide side, int positionIdx, String createType, String cancelType,
      String rejectReason, BigDecimal leavesQty, BigDecimal leavesValue,
      BigDecimal cumExecQty, BigDecimal cumExecValue, BigDecimal cumExecFee, String feeCurrency,
      TimeInForce timeInForce, BybitOrderType orderType, String stopOrderType, String ocoTriggerBy,
      String orderIv, String marketUnit, BigDecimal triggerPrice, BigDecimal takeProfit,
      BigDecimal stopLoss, String tpslMode, BigDecimal tpLimitPrice, BigDecimal slLimitPrice,
      String tpTriggerBy, String slTriggerBy, int triggerDirection, String triggerBy,
      String lastPriceOnCreated, boolean reduceOnly, boolean closeOnTrigger, String placeType,
      String smpType, int smpGroup, String smpOrderId, Date updatedTime) {
    super(type, originalAmount, instrument, orderId, createdTime, averagePrice, cumulativeAmount,
        fee, orderStatus, orderLinkId);
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
    this.cumExecQty = cumExecQty;
    this.cumExecValue = cumExecValue;
    this.cumExecFee = cumExecFee;
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
