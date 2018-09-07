package org.knowm.xchange.bity.service;

import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bity.dto.marketdata.BityPair;

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
    return bity.getPairs().getPairs();
  }
}
