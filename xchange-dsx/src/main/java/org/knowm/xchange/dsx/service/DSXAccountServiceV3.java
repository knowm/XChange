package org.knowm.xchange.dsx.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dsx.DSXAuthenticatedV3;
import org.knowm.xchange.dsx.service.core.DSXAccountServiceCore;

/** @author Pavel Chertalev */
public class DSXAccountServiceV3 extends DSXAccountServiceCore<DSXAuthenticatedV3> {
  public DSXAccountServiceV3(Exchange exchange) {
    super(exchange, DSXAuthenticatedV3.class);
  }
}
