package org.knowm.xchange.ascendex.dto.trade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.knowm.xchange.ascendex.dto.enums.AscendexOrderType;
import org.knowm.xchange.ascendex.dto.enums.AscendexRespInst;
import org.knowm.xchange.ascendex.dto.enums.AscendexSide;
import org.knowm.xchange.ascendex.dto.enums.AscendexTimeInForce;

public class AscendexPlaceOrderRequestPayload {

  private final String symbol;

  private final Long time;

  private final String orderQty;

  private final AscendexOrderType orderType;

  private final AscendexSide side;

  @JsonIgnore private final String id;

  private final String orderPrice;

  @JsonIgnore private final String stopPrice;
  /**
   * 只挂单
   */
  private final boolean postOnly;

  // GTC or OIC, default GTC
  @JsonIgnore private final AscendexTimeInForce timeInForce;

  private final AscendexRespInst respInst;

  public AscendexPlaceOrderRequestPayload(
      String symbol,
      Long time,
      String orderQty,
      AscendexOrderType orderType,
      AscendexSide side,
      String id,
      String orderPrice,
      String stopPrice,
      boolean postOnly,
      AscendexTimeInForce timeInForce,
      AscendexRespInst respInst) {
    this.symbol = symbol==null?null:symbol.toUpperCase();
    this.time = time;
    this.orderQty = orderQty;
    this.orderType = orderType;
    this.side = side;
    this.id = id;
    this.orderPrice = orderPrice;
    this.stopPrice = stopPrice;
    this.postOnly = postOnly;
    this.timeInForce = timeInForce;
    this.respInst = respInst;
  }

  public String getSymbol() {
    return symbol;
  }

  public Long getTime() {
    return time;
  }

  public String getOrderQty() {
    return orderQty;
  }

  public AscendexOrderType getOrderType() {
    return orderType;
  }

  public AscendexSide getSide() {
    return side;
  }

  public String getId() {
    return id;
  }

  public String getOrderPrice() {
    return orderPrice;
  }

  public String getStopPrice() {
    return stopPrice;
  }

  public boolean isPostOnly() {
    return postOnly;
  }

  public AscendexTimeInForce getTimeInForce() {
    return timeInForce;
  }

  public AscendexRespInst getRespInst() {
    return respInst;
  }

  @Override
  public String toString() {
    return "AscendexPlaceOrderRequestPayload{"
        + "symbol='"
        + symbol
        + '\''
        + ", time="
        + time
        + ", orderQty="
        + orderQty
        + ", orderType="
        + orderType
        + ", side="
        + side
        + ", id='"
        + id
        + '\''
        + ", orderPrice="
        + orderPrice
        + ", stopPrice="
        + stopPrice
        + ", postOnly="
        + postOnly
        + ", timeInForce='"
        + timeInForce
        + '\''
        + ", respInst='"
        + respInst
        + '\''
        + '}';
  }




}
