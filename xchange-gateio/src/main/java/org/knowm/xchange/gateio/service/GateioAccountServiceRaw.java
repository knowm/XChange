package org.knowm.xchange.gateio.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.gateio.dto.account.GateioFunds;

public class GateioAccountServiceRaw extends GateioBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GateioAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public GateioFunds getGateioAccountInfo() throws IOException {

    GateioFunds gateioFunds = bter.getFunds(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());
    return handleResponse(gateioFunds);
  }

}
