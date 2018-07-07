package org.knowm.xchange.cobinhood.dto.trading;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.cobinhood.dto.CobinhoodOrderSide;
import org.knowm.xchange.cobinhood.dto.CobinhoodOrderType;
import org.knowm.xchange.dto.Order;

public class CobinhoodOrder {
  private final String id;
  private final String tradingPair;
  private final State state;
  private final CobinhoodOrderSide side;
  private final CobinhoodOrderType type;
  private final BigDecimal price;
  private final BigDecimal size;
  private final BigDecimal filled;
  private final Long timestamp;

  public CobinhoodOrder(
      @JsonProperty("id") String id,
      @JsonProperty("trading_pair_id") String tradingPair,
      @JsonProperty("state") State state,
      @JsonProperty("side") CobinhoodOrderSide side,
      @JsonProperty("type") CobinhoodOrderType type,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("filled") BigDecimal filled,
      @JsonProperty("timestamp") Long timestamp) {
    this.id = id;
    this.tradingPair = tradingPair;
    this.state = state;
    this.side = side;
    this.type = type;
    this.price = price;
    this.size = size;
    this.filled = filled;
    this.timestamp = timestamp;
  }

  public String getId() {
    return id;
  }

  public String getTradingPair() {
    return tradingPair;
  }

  public State getState() {
    return state;
  }

  public CobinhoodOrderSide getSide() {
    return side;
  }

  public CobinhoodOrderType getType() {
    return type;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getFilled() {
    return filled;
  }

  public BigDecimal getSize() {
    return size;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public enum State {
    queued(Order.OrderStatus.PENDING_NEW),
    open(Order.OrderStatus.NEW),
    partially_filled(Order.OrderStatus.PARTIALLY_FILLED),
    filled(Order.OrderStatus.FILLED),
    canceled(Order.OrderStatus.CANCELED);

    private final Order.OrderStatus status;

    State(Order.OrderStatus status) {
      this.status = status;
    }

    public Order.OrderStatus getStatus() {
      return status;
    }
  }

  public static class Container {
    private final CobinhoodOrder order;

    public Container(@JsonProperty("order") CobinhoodOrder order) {
      this.order = order;
    }

    public CobinhoodOrder getOrder() {
      return order;
    }
  }
}
