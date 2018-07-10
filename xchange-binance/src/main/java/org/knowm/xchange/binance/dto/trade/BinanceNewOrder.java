package org.knowm.xchange.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.dto.trade.LimitOrder;

public final class BinanceNewOrder extends LimitOrder {

  public final String symbol;
  public final long orderId;
  public final String clientOrderId;
  public final long transactTime;
  public final BigDecimal price;
  public final BigDecimal origQty;
  public final BigDecimal executedQty;
  public final BinanceOrderStatus bStatus;
  public final TimeInForce timeInForce;
  public final BinanceOrderType bType;
  public final OrderSide side;

  public BinanceNewOrder(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("orderId") long orderId,
      @JsonProperty("clientOrderId") String clientOrderId,
      @JsonProperty("transactTime") long transactTime,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("origQty") BigDecimal origQty,
      @JsonProperty("executedQty") BigDecimal executedQty,
      @JsonProperty("status") BinanceOrderStatus status,
      @JsonProperty("timeInForce") TimeInForce timeInForce,
      @JsonProperty("type") BinanceOrderType type,
      @JsonProperty("side") OrderSide side) {
    super(
        BinanceAdapters.convert(side),
        origQty,
        BinanceAdapters.adaptSymbol(symbol),
        Long.toString(orderId),
        new Date(transactTime),
        null);
    this.symbol = symbol;
    this.orderId = orderId;
    this.clientOrderId = clientOrderId;
    this.transactTime = transactTime;
    this.price = price;
    this.origQty = origQty;
    this.executedQty = executedQty;
    this.bStatus = status;
    this.timeInForce = timeInForce;
    this.bType = type;
    this.side = side;
  }
}
