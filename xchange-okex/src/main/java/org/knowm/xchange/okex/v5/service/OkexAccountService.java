package org.knowm.xchange.okex.v5.service;

import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.okex.v5.OkexExchange;
import org.knowm.xchange.service.account.AccountService;

/** Author: Max Gao (gaamox@tutanota.com) Created: 08-06-2021 */
public class OkexAccountService extends OkexAccountServiceRaw implements AccountService {
  public OkexAccountService(OkexExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }
}
