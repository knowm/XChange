package com.xeiam.xchange.empoex.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.empoex.EmpoExAdapters;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

public class EmpoExAccountService extends EmpoExAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public EmpoExAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return EmpoExAdapters.adaptBalances(super.getEmpoExBalances().get("available"));
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
