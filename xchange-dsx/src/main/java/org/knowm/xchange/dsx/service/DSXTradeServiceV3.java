package org.knowm.xchange.dsx.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dsx.DSXAuthenticatedV3;
import org.knowm.xchange.dsx.service.core.DSXTradeServiceCore;

/** @author Pavel Chertalev */
public class DSXTradeServiceV3 extends DSXTradeServiceCore<DSXAuthenticatedV3> {
  public DSXTradeServiceV3(Exchange exchange) {
    super(exchange, DSXAuthenticatedV3.class);
  }
}
