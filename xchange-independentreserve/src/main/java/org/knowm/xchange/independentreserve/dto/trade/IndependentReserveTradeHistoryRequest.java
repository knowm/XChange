package org.knowm.xchange.independentreserve.dto.trade;

import org.knowm.xchange.independentreserve.dto.auth.AuthAggregate;

/** Author: Kamil Zbikowski Date: 4/16/15 */
public class IndependentReserveTradeHistoryRequest extends AuthAggregate {

  public IndependentReserveTradeHistoryRequest(
      String apiKey, Long nonce, String pageIndex, String pageSize) {
    super(apiKey, nonce);

    this.parameters.put("pageIndex", pageIndex);
    this.parameters.put("pageSize", pageSize);
  }
}
