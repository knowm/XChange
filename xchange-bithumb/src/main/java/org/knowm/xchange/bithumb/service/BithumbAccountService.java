package org.knowm.xchange.bithumb.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bithumb.BithumbAdapters;
import org.knowm.xchange.bithumb.dto.account.BithumbWalletAddress;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;

public class BithumbAccountService extends BithumbAccountServiceRaw implements AccountService {

  protected BithumbAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return BithumbAdapters.adaptAccountInfo(getBithumbAddress(), getBithumbBalance());
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    return getBithumbWalletAddress(currency)
        .map(BithumbWalletAddress::getWalletAddress)
        .orElse(null);
  }
}
