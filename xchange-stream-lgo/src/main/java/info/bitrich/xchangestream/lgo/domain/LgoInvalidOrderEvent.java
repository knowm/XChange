package info.bitrich.xchangestream.lgo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.Map;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

/** Order was invalid */
public class LgoInvalidOrderEvent extends LgoBatchOrderEvent {

  /**
   * Reason of invalidity (InvalidQuantity, InvalidPrice, InvalidAmount, InvalidPriceIncrement,
   * InvalidProduct, InsufficientFunds)
   */
  private final String reason;

  public LgoInvalidOrderEvent(Long batchId, String type, String orderId, Date time, String reason) {
    super(batchId, type, orderId, time);
    this.reason = reason;
  }

  public LgoInvalidOrderEvent(
      @JsonProperty("type") String type,
      @JsonProperty("order_id") String orderId,
      @JsonProperty("time")
          @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
          Date time,
      @JsonProperty("reason") String reason) {
    super(type, orderId, time);
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }

  @Override
  public Order applyOnOrders(CurrencyPair currencyPair, Map<String, Order> allOrders) {
    Order doneOrder = allOrders.remove(getOrderId());
    doneOrder.setOrderStatus(Order.OrderStatus.REJECTED);
    return doneOrder;
  }
}
