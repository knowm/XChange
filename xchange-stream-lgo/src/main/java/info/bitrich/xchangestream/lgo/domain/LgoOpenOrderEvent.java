package info.bitrich.xchangestream.lgo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.Map;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

/** Order entered order book */
public class LgoOpenOrderEvent extends LgoBatchOrderEvent {

  public LgoOpenOrderEvent(Long batchId, String type, String orderId, Date time) {
    super(batchId, type, orderId, time);
  }

  public LgoOpenOrderEvent(
      @JsonProperty("type") String type,
      @JsonProperty("order_id") String orderId,
      @JsonProperty("time")
          @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
          Date time) {
    super(type, orderId, time);
  }

  @Override
  public Order applyOnOrders(CurrencyPair currencyPair, Map<String, Order> allOrders) {
    Order pendingOrder = allOrders.get(getOrderId());
    Order.OrderStatus status =
        pendingOrder.getStatus().equals(Order.OrderStatus.PARTIALLY_FILLED)
            ? pendingOrder.getStatus()
            : Order.OrderStatus.NEW;
    pendingOrder.setOrderStatus(status);
    return pendingOrder;
  }
}
