package org.knowm.xchange.bity.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bity.BityExchange;
import org.knowm.xchange.bity.dto.BityResponse;
import org.knowm.xchange.bity.dto.account.BityOrder;

class BityTradeServiceRaw extends BityBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BityTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BityResponse<BityOrder> getBityOrders(Long offset, final Integer limit, String orderBy) {
    BityExchange bityExchange = (BityExchange) exchange;
    String auth = "Bearer " + bityExchange.getToken().getAccessToken();
    return bity.getOrders(offset, limit, orderBy, auth);
  }
}
