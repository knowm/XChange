package org.knowm.xchange.kuna.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.account.AccountService;

/** @author Dat Bui */
public class KunaAccountService extends KunaAccountServiceRaw implements AccountService {

  /**
   * Constructor.
   *
   * @param exchange
   */
  public KunaAccountService(Exchange exchange) {
    super(exchange);
  }
}
