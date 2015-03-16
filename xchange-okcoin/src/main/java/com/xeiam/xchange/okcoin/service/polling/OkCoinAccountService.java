package com.xeiam.xchange.okcoin.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.okcoin.OkCoinAdapters;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

public class OkCoinAccountService extends OkCoinAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public OkCoinAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return OkCoinAdapters.adaptAccountInfo(getUserInfo());
  }

  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String requestDepositAddress(String currency, String... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

}
