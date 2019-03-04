package org.knowm.xchange.simulated;

import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.account.AccountService;

public class SimulatedAccountService extends BaseExchangeService<SimulatedExchange> implements AccountService {

  protected SimulatedAccountService(SimulatedExchange exchange) {
    super(exchange);
  }

}
