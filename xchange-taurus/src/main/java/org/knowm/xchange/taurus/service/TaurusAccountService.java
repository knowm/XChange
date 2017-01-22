package org.knowm.xchange.taurus.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.taurus.TaurusAdapters;

/**
 * @author Matija Mazi
 */
public class TaurusAccountService extends TaurusAccountServiceRaw implements AccountService {

  public TaurusAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return TaurusAdapters.adaptAccountInfo(getTaurusBalance(), exchange.getExchangeSpecification().getUserName());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    return withdrawTaurusFunds(amount, address);
  }

  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {
    return getTaurusBitcoinDepositAddress();
  }
}
