package org.knowm.xchange.bithumb.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bithumb.BithumbAdapters;
import org.knowm.xchange.bithumb.BithumbErrorAdapter;
import org.knowm.xchange.bithumb.BithumbException;
import org.knowm.xchange.bithumb.dto.account.BithumbWalletAddress;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;

public class BithumbAccountService extends BithumbAccountServiceRaw implements AccountService {

  public BithumbAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    try {
      return BithumbAdapters.adaptAccountInfo(getBithumbAddress(), getBithumbBalance());
    } catch (BithumbException e) {
      throw BithumbErrorAdapter.adapt(e);
    }
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    try {
      return getBithumbWalletAddress(currency)
          .map(BithumbWalletAddress::getWalletAddress)
          .orElse(null);
    } catch (BithumbException e) {
      throw BithumbErrorAdapter.adapt(e);
    }
  }
}
