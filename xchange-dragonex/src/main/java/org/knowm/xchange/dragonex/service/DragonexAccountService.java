package org.knowm.xchange.dragonex.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dragonex.dto.account.Balance;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.service.account.AccountService;

public class DragonexAccountService extends DragonexAccountServiceRaw implements AccountService {

  public DragonexAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    List<Balance> userCoins = userCoins(exchange.getOrCreateToken().token);
    List<org.knowm.xchange.dto.account.Balance> balances =
        userCoins
            .stream()
            .map(
                b ->
                    new org.knowm.xchange.dto.account.Balance(
                        Currency.getInstance(b.code.toUpperCase()),
                        b.volume,
                        b.volume.subtract(b.frozen),
                        b.frozen))
            .collect(Collectors.toList());
    return new AccountInfo(new Wallet(balances));
  }
}
