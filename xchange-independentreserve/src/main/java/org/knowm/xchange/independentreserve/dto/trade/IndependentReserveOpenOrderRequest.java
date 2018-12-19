package org.knowm.xchange.independentreserve.dto.trade;

import org.knowm.xchange.independentreserve.dto.auth.AuthAggregate;

/** Author: Kamil Zbikowski Date: 4/14/15 */
public class IndependentReserveOpenOrderRequest extends AuthAggregate {

  public IndependentReserveOpenOrderRequest(
      String apiKey,
      Long nonce,
      String primaryCurrencyCode,
      String secondaryCurrencyCode,
      int pageIndex,
      int pageSize) {
    super(apiKey, nonce);

    if (primaryCurrencyCode != null) {
      this.parameters.put("primaryCurrencyCode", primaryCurrencyCode);
    }

    if (secondaryCurrencyCode != null) {
      this.parameters.put("secondaryCurrencyCode", secondaryCurrencyCode);
    }

    this.parameters.put("pageIndex", pageIndex);
    this.parameters.put("pageSize", pageSize);
  }
}
