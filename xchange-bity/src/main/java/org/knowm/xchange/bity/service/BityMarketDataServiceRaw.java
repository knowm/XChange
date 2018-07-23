package org.knowm.xchange.bity.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bity.dto.marketdata.BityPair;

import java.util.List;

public class BityMarketDataServiceRaw extends BityBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BityMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<BityPair> getBityPairs() {
    return bity.getPairs().getObjects();
  }
}
