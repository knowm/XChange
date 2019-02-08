package org.knowm.xchange.btcturk.dto.trade;

/** @author mertguner */
public class BTCTurkCancelOrderRequest {
  private long id;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "BTCTurkCancelOrderRequest [id=" + id + "]";
  }
}
