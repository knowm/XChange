package org.knowm.xchange.taurus.dto.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.taurus.dto.TaurusBaseResponse;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;
import org.knowm.xchange.utils.jackson.SqlUtcTimeDeserializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author Matija Mazi
 */
public final class TaurusOrder extends TaurusBaseResponse {

  private final String id;
  private final Date datetime;
  private final Order.OrderType type;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final Status status;
  private final CurrencyPair book;

  public TaurusOrder(@JsonProperty("id") String id, @JsonProperty("datetime") @JsonDeserialize(using = SqlUtcTimeDeserializer.class) Date datetime,
      @JsonProperty("type") Order.OrderType type, @JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("status") Status status, @JsonProperty("error") Object error,
      @JsonProperty("book") @JsonDeserialize(using = CurrencyPairDeserializer.class) CurrencyPair book) {
    super(error);
    this.id = id;
    this.datetime = datetime;
    this.type = type;
    this.price = price;
    this.amount = amount;
    this.status = status;
    this.book = book;
  }

  public Date getDatetime() {
    return datetime;
  }

  public String getId() {
    return id;
  }

  public Order.OrderType getType() {
    return type;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public Status getStatus() {
    return status;
  }

  public CurrencyPair getBook() {
    return book;
  }

  @JsonDeserialize(using = StatusDeserializer.class)
  public enum Status {
    cancelled, active, partiallyFilled, complete, unknown
  }

  /**
   * (-1 - cancelled; 0 - active; 1 - partially filled; 2 - complete)
   */
  public static class StatusDeserializer extends JsonDeserializer<Status> {
    @Override
    public Status deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
      switch (jp.getValueAsInt()) {
        case -1:
          return Status.cancelled;
        case 0:
          return Status.active;
        case 1:
          return Status.partiallyFilled;
        case 2:
          return Status.complete;
      }
      return Status.unknown;
    }
  }

  @Override
  public String toString() {
    return String.format("Order{id=%s, datetime=%s, type=%s, price=%s, amount=%s}", id, datetime, type, price, amount);
  }
}
