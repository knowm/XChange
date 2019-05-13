package org.knowm.xchange.dsx.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dsx.DSXAuthenticatedV3;
import org.knowm.xchange.dsx.service.core.DSXMarketDataServiceCore;

/** @author Pavel Chertalev */
public class DSXMarketDataServiceV3 extends DSXMarketDataServiceCore<DSXAuthenticatedV3> {
  public DSXMarketDataServiceV3(Exchange exchange) {
    super(exchange, DSXAuthenticatedV3.class);
  }
}
