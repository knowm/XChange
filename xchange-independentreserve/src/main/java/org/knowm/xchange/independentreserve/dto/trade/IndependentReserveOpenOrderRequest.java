package org.knowm.xchange.independentreserve.dto.trade;

import org.knowm.xchange.independentreserve.dto.auth.AuthAggregate;

/**
 * Author: Kamil Zbikowski Date: 4/14/15
 */
public class IndependentReserveOpenOrderRequest extends AuthAggregate {

  public IndependentReserveOpenOrderRequest(String apiKey, Long nonce, String primaryCurrencyCode, String secondaryCurrencyCode, String pageIndex,
      String pageSize) {
    super(apiKey, nonce);

    if (primaryCurrencyCode != null) {
      if (primaryCurrencyCode.equals("BTC")) {
        primaryCurrencyCode = "Xbt";
      } else if (primaryCurrencyCode.equals("ETH")) {
        primaryCurrencyCode = "Eth";
      } else {
        throw new IllegalArgumentException("IndependentReserveOpenOrderRequest - unknown value of base currency code");
      }
      this.parameters.put("primaryCurrencyCode", primaryCurrencyCode);
    }

    if (secondaryCurrencyCode != null) {
      if (secondaryCurrencyCode.equals("USD")) {
        secondaryCurrencyCode = "Usd";
      } else if (secondaryCurrencyCode.equals("AUD")) {
        secondaryCurrencyCode = "Aud";
      } else if (secondaryCurrencyCode.equals("NZD")) {
        secondaryCurrencyCode = "Nzd";
      } else {
        throw new IllegalArgumentException("IndependentReserveOpenOrderRequest - unknown value of counter currency code");
      }
      this.parameters.put("secondaryCurrencyCode", secondaryCurrencyCode);
    }

    this.parameters.put("pageIndex", pageIndex);
    this.parameters.put("pageSize", pageSize);

  }
}
