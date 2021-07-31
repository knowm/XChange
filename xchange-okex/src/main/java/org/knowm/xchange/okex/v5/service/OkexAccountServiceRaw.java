package org.knowm.xchange.okex.v5.service;

import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.okex.v5.OkexExchange;

/** Author: Max Gao (gaamox@tutanota.com) Created: 08-06-2021 */
public class OkexAccountServiceRaw extends OkexBaseService {
  public OkexAccountServiceRaw(OkexExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }
}
