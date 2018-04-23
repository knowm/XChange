package org.knowm.xchange.bitcoinde.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcoinde.BitcoindeAdapters;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;

/** @author kaiserfr */
public class BitcoindeAccountService extends BitcoindeAccountServiceRaw implements AccountService {

  public BitcoindeAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return BitcoindeAdapters.adaptAccountInfo(getBitcoindeAccount());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
