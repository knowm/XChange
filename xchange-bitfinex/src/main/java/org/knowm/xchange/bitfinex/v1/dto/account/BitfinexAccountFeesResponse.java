package org.knowm.xchange.bitfinex.v1.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.currency.Currency;

/** @author ujjwal on 23/02/18. */
public class BitfinexAccountFeesResponse {
  private final Map<Currency, BigDecimal> withdraw;

  public BitfinexAccountFeesResponse(
      @JsonProperty("withdraw") final Map<Currency, BigDecimal> withdraw) {
    this.withdraw = new HashMap<>(withdraw);
  }

  public Map<Currency, BigDecimal> getWithdraw() {
    return withdraw;
  }
}
