package org.knowm.xchange.dsx.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dsx.DSXAuthenticatedV2;
import org.knowm.xchange.dsx.service.core.DSXTradeServiceCore;

/** @author Pavel Chertalev */
public class DSXTradeService extends DSXTradeServiceCore<DSXAuthenticatedV2> {
  public DSXTradeService(Exchange exchange) {
    super(exchange, DSXAuthenticatedV2.class);
  }
}
