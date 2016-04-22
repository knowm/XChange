package org.knowm.xchange.independentreserve.dto.trade;

import org.knowm.xchange.independentreserve.dto.auth.AuthAggregate;

/**
 * Author: Kamil Zbikowski Date: 4/14/15
 */
public class IndependentReserveOpenOrderRequest extends AuthAggregate {

  public IndependentReserveOpenOrderRequest(String apiKey, Long nonce, String primaryCurrencyCode, String secondaryCurrencyCode, String pageIndex,
      String pageSize) {
    super(apiKey, nonce);

    if (primaryCurrencyCode.equals("BTC")) {
      primaryCurrencyCode = "Xbt";
    } else {
      throw new IllegalArgumentException("IndependentReserveOpenOrderRequest - unknown value of base currency code");
    }
    if (secondaryCurrencyCode.equals("USD")) {
      secondaryCurrencyCode = "Usd";
    } else {
      throw new IllegalArgumentException("IndependentReserveOpenOrderRequest - unknown value of counter currency code");
    }

    this.parameters.put("primaryCurrencyCode", primaryCurrencyCode);
    this.parameters.put("secondaryCurrencyCode", secondaryCurrencyCode);
    this.parameters.put("pageIndex", pageIndex);
    this.parameters.put("pageSize", pageSize);

  }
}
