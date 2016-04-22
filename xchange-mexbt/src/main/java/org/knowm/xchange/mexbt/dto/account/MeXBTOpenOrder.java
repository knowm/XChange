package org.knowm.xchange.mexbt.dto.account;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.knowm.xchange.mexbt.dto.TickDeserializer;

public class MeXBTOpenOrder {

  private final long serverOrderId;
  private final long accountId;
  private final BigDecimal price;
  private final BigDecimal qtyTotal;
  private final BigDecimal qtyRemaining;
  private final Date receiveTime;
  private final int side;

  public MeXBTOpenOrder(@JsonProperty("ServerOrderId") long serverOrderId, @JsonProperty("AccountId") long accountId,
      @JsonProperty("Price") BigDecimal price, @JsonProperty("QtyTotal") BigDecimal qtyTotal, @JsonProperty("QtyRemaining") BigDecimal qtyRemaining,
      @JsonProperty("ReceiveTime") @JsonDeserialize(using = TickDeserializer.class) Date receiveTime, @JsonProperty("Side") int side) {
    this.serverOrderId = serverOrderId;
    this.accountId = accountId;
    this.price = price;
    this.qtyTotal = qtyTotal;
    this.qtyRemaining = qtyRemaining;
    this.receiveTime = receiveTime;
    this.side = side;
  }

  public long getServerOrderId() {
    return serverOrderId;
  }

  public long getAccountId() {
    return accountId;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getQtyTotal() {
    return qtyTotal;
  }

  public BigDecimal getQtyRemaining() {
    return qtyRemaining;
  }

  public Date getReceiveTime() {
    return receiveTime;
  }

  public int getSide() {
    return side;
  }

}
