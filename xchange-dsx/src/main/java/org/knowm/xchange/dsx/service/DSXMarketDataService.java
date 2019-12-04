package org.knowm.xchange.dsx.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dsx.DSXAuthenticatedV2;
import org.knowm.xchange.dsx.service.core.DSXMarketDataServiceCore;

/** @author Pavel Chertalev */
public class DSXMarketDataService extends DSXMarketDataServiceCore<DSXAuthenticatedV2> {
  public DSXMarketDataService(Exchange exchange) {
    super(exchange, DSXAuthenticatedV2.class);
  }
}
