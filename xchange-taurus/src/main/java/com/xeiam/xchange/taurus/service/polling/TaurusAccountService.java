package com.xeiam.xchange.taurus.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.account.PollingAccountService;
import com.xeiam.xchange.taurus.TaurusAdapters;

/**
 * @author Matija Mazi
 */
public class TaurusAccountService extends TaurusAccountServiceRaw implements PollingAccountService {

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
