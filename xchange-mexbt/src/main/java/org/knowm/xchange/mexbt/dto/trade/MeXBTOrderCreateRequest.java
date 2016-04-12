package org.knowm.xchange.mexbt.dto.trade;

import java.math.BigDecimal;

import org.knowm.xchange.mexbt.dto.MeXBTInsRequest;
import org.knowm.xchange.mexbt.service.MeXBTDigest;

import si.mazi.rescu.SynchronizedValueFactory;

public class MeXBTOrderCreateRequest extends MeXBTInsRequest {

  private final String side;
  private final int orderType;
  private final BigDecimal qty;
  private final BigDecimal px;

  public MeXBTOrderCreateRequest(String apiKey, SynchronizedValueFactory<Long> nonceFactory, MeXBTDigest meXBTDigest, String ins, String side,
      int orderType, BigDecimal qty, BigDecimal px) {
    super(apiKey, nonceFactory, meXBTDigest, ins);
    this.side = side;
    this.orderType = orderType;
    this.qty = qty;
    this.px = px;
  }

  public String getSide() {
    return side;
  }

  public int getOrderType() {
    return orderType;
  }

  public BigDecimal getQty() {
    return qty;
  }

  public BigDecimal getPx() {
    return px;
  }

}
