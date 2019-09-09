package org.knowm.xchange.simulated;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.account.AccountService;

public class SimulatedAccountService extends BaseExchangeService<SimulatedExchange>
    implements AccountService {

  protected SimulatedAccountService(SimulatedExchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    exchange.maybeThrow();
    return new AccountInfo(Wallet.Builder.from(exchange.getAccount().balances()).build());
  }

  public void deposit(Currency currency, BigDecimal amount) {
    exchange.getAccount().deposit(currency, amount);
  }
}
