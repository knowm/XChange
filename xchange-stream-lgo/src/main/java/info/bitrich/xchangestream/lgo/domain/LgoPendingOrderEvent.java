package info.bitrich.xchangestream.lgo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.lgo.LgoAdapter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

/** Order was received by LGO execution engine in a batch */
public class LgoPendingOrderEvent extends LgoBatchOrderEvent {

  /** Type of Order (L for LimitOrder, M for MarketOrder) when type=pending */
  private final String orderType;

  /** Limit price (quote currency) when type=pending and orderType=L */
  private final BigDecimal limitPrice;

  /** Side of the order: BID/ASK when type=pending */
  private final Order.OrderType side;

  /** Initial amount (base currency) when type=pending */
  private final BigDecimal initialAmount;

  public LgoPendingOrderEvent(
      @JsonProperty("type") String type,
      @JsonProperty("order_id") String orderId,
      @JsonProperty("time")
          @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
          Date time,
      @JsonProperty("order_type") String orderType,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("side") String side,
      @JsonProperty("quantity") BigDecimal quantity) {
    super(type, orderId, time);
    this.orderType = orderType;
    this.limitPrice = price;
    this.side = side.equals("B") ? Order.OrderType.BID : Order.OrderType.ASK;
    this.initialAmount = quantity;
  }

  public LgoPendingOrderEvent(
      Long batchId,
      String type,
      String orderId,
      Date time,
      String orderType,
      BigDecimal limitPrice,
      Order.OrderType side,
      BigDecimal initialAmount) {
    super(batchId, type, orderId, time);
    this.orderType = orderType;
    this.limitPrice = limitPrice;
    this.side = side;
    this.initialAmount = initialAmount;
  }

  @Override
  public Order applyOnOrders(CurrencyPair currencyPair, Map<String, Order> allOrders) {
    Order order = LgoAdapter.adaptPendingOrder(this, currencyPair);
    allOrders.put(order.getId(), order);
    return order;
  }

  public String getOrderType() {
    return orderType;
  }

  public BigDecimal getLimitPrice() {
    return limitPrice;
  }

  public Order.OrderType getSide() {
    return side;
  }

  public BigDecimal getInitialAmount() {
    return initialAmount;
  }
}
