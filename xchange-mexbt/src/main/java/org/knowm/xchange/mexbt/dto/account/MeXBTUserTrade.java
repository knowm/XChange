package org.knowm.xchange.mexbt.dto.account;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.knowm.xchange.mexbt.dto.TickDeserializer;

public class MeXBTUserTrade {

  private final long tid;
  private final BigDecimal px;
  private final BigDecimal qty;
  private final Date time;
  private final int incomingOrderSide;
  private final long incomingServerOrderId;
  private final long bookServerOrderId;

  public MeXBTUserTrade(@JsonProperty("tid") long tid, @JsonProperty("px") BigDecimal px, @JsonProperty("qty") BigDecimal qty,
      @JsonProperty("time") @JsonDeserialize(using = TickDeserializer.class) Date time, @JsonProperty("incomingOrderSide") int incomingOrderSide,
      @JsonProperty("incomingServerOrderId") long incomingServerOrderId, @JsonProperty("bookServerOrderId") long bookServerOrderId) {
    super();
    this.tid = tid;
    this.px = px;
    this.qty = qty;
    this.time = time;
    this.incomingOrderSide = incomingOrderSide;
    this.incomingServerOrderId = incomingServerOrderId;
    this.bookServerOrderId = bookServerOrderId;
  }

  public long getTid() {
    return tid;
  }

  public BigDecimal getPx() {
    return px;
  }

  public BigDecimal getQty() {
    return qty;
  }

  public Date getTime() {
    return time;
  }

  public int getIncomingOrderSide() {
    return incomingOrderSide;
  }

  public long getIncomingServerOrderId() {
    return incomingServerOrderId;
  }

  public long getBookServerOrderId() {
    return bookServerOrderId;
  }

}
