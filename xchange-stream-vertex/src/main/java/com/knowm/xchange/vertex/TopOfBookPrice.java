package com.knowm.xchange.vertex;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TopOfBookPrice {
  private final BigDecimal bid;
  private final BigDecimal offer;

  public TopOfBookPrice(BigDecimal bid, BigDecimal offer) {

    this.bid = bid;
    this.offer = offer;
  }
}
