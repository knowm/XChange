package org.knowm.xchange.cointrader.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * @author Matija Mazi
 */
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class CointraderBalance {
  private BigDecimal available;
  private BigDecimal onHold;
  private BigDecimal total;

  public BigDecimal getAvailable() {
    return available;
  }

  public BigDecimal getOnHold() {
    return onHold;
  }

  public BigDecimal getTotal() {
    return total;
  }
}
