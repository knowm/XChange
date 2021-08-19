package info.bitrich.xchangestream.lgo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

/** Order left orderbook */
public class LgoDoneOrderEvent extends LgoBatchOrderEvent {

  /** Reason of done status (rejected, canceled, canceledBySelfTradePrevention, filled) */
  private final String reason;

  /** Amount canceled (base currency) when reason=canceledBySelfTradePrevention */
  private final BigDecimal canceled;

  public LgoDoneOrderEvent(
      Long batchId, String type, String orderId, Date time, String reason, BigDecimal canceled) {
    super(batchId, type, orderId, time);
    this.reason = reason;
    this.canceled = canceled;
  }

  public LgoDoneOrderEvent(
      @JsonProperty("type") String type,
      @JsonProperty("order_id") String orderId,
      @JsonProperty("time")
          @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
          Date time,
      @JsonProperty("reason") String reason,
      @JsonProperty("canceled") BigDecimal canceled) {
    super(type, orderId, time);
    this.reason = reason;
    this.canceled = canceled;
  }

  public String getReason() {
    return reason;
  }

  public BigDecimal getCanceled() {
    return canceled;
  }

  @Override
  public Order applyOnOrders(CurrencyPair currencyPair, Map<String, Order> allOrders) {
    Order doneOrder = allOrders.remove(getOrderId());
    if ("canceledBySelfTradePrevention".equals(reason) || "canceled".equals(reason)) {
      doneOrder.setOrderStatus(
          doneOrder.getStatus() == Order.OrderStatus.PARTIALLY_FILLED
              ? Order.OrderStatus.PARTIALLY_CANCELED
              : Order.OrderStatus.CANCELED);
    } else if ("filled".equals(reason)) {
      doneOrder.setOrderStatus(Order.OrderStatus.FILLED);
    } else {
      doneOrder.setOrderStatus(Order.OrderStatus.REJECTED);
    }
    return doneOrder;
  }
}
