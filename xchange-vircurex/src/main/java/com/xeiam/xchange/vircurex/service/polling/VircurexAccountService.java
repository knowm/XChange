package com.xeiam.xchange.vircurex.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.account.PollingAccountService;
import com.xeiam.xchange.vircurex.VircurexAdapters;

public class VircurexAccountService extends VircurexAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public VircurexAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return VircurexAdapters.adaptAccountInfo(getVircurexAccountInfo());
  }

  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {

    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String withdrawFunds(Currency currecny, BigDecimal amount, String address) throws IOException {

    throw new NotYetImplementedForExchangeException();

  }
}
