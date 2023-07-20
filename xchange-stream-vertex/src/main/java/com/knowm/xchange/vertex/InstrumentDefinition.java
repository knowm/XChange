package com.knowm.xchange.vertex;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class InstrumentDefinition {

  private final BigDecimal priceIncrement;
  private final BigDecimal quantityIncrement;

  public InstrumentDefinition(BigDecimal priceIncrement, BigDecimal quantityIncrement) {
    this.priceIncrement = priceIncrement;
    this.quantityIncrement = quantityIncrement;
  }
}
