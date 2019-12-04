package org.knowm.xchange.dsx.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dsx.DSXAuthenticatedV2;
import org.knowm.xchange.dsx.service.core.DSXAccountServiceCore;

/** @author Pavel Chertalev */
public class DSXAccountService extends DSXAccountServiceCore<DSXAuthenticatedV2> {
  public DSXAccountService(Exchange exchange) {
    super(exchange, DSXAuthenticatedV2.class);
  }
}
