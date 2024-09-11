package org.knowm.xchange.bybit.dto.trade;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.trade.BybitAdvancedOrder.SlTriggerBy;
import org.knowm.xchange.bybit.dto.trade.BybitAdvancedOrder.TimeInForce;
import org.knowm.xchange.instrument.Instrument;

@Getter
@Setter
public class BybitPlaceOrderPayload {

  private final String category;
  private final String symbol;
  private final String side;
  private final String orderType;
  private String qty;
  private String orderLinkId;
  private String price;
  private String stopLoss;
  private String slTriggerBy;
  private String slLimitPrice;
  private String slOrderType;
  private String tpslMode;
  private String reduceOnly = "false";
  private int positionIdx = 0;
  private TimeInForce timeInForce;

  public BybitPlaceOrderPayload(BybitCategory category, String symbol, BybitSide side,
      BybitOrderType orderType, BigDecimal qty, String orderLinkId) {
    this.category = category.getValue();
    this.symbol = symbol;
    this.side = side.getValue();
    this.orderType = orderType.getValue();
    this.qty = qty.toString();
    this.orderLinkId = orderLinkId;
  }
  public BybitPlaceOrderPayload(BybitCategory category, String symbol, BybitSide side, BybitOrderType orderType,
      BigDecimal qty, String orderLinkId, int positionIdx, BigDecimal price) {
    this.category = category.getValue();
    this.symbol = symbol;
    this.side = side.getValue();
    this.orderType = orderType.getValue();
    this.qty = qty.toString();
    this.orderLinkId = orderLinkId;
    this.positionIdx = positionIdx;
    this.price = price.toString();
  }

  public BybitPlaceOrderPayload(BybitCategory category, String symbol, BybitSide side, BybitOrderType orderType,
      BigDecimal qty, String orderLinkId,  BigDecimal price) {
    this.category = category.getValue();
    this.symbol = symbol;
    this.side = side.getValue();
    this.orderType = orderType.getValue();
    this.qty = qty.toString();
    this.orderLinkId = orderLinkId;
    this.price = price.toString();
  }

  public BybitPlaceOrderPayload(BybitCategory category, String symbol, BybitSide side,
      BybitOrderType orderType, BigDecimal qty, String orderLinkId, BigDecimal price,
      BigDecimal stopLoss, SlTriggerBy slTriggerBy, BigDecimal slLimitPrice, BybitOrderType slOrderType) {
    this.category = category.getValue();
    this.symbol = symbol;
    this.side = side.getValue();
    this.orderType = orderType.getValue();
    this.qty = qty.toString();
    this.orderLinkId = orderLinkId;
    this.price = price.toString();
    this.stopLoss = stopLoss.toString();
    this.slTriggerBy = slTriggerBy.getValue();
    this.slLimitPrice = slLimitPrice.toString();
    this.slOrderType = slOrderType.getValue();
  }
}
