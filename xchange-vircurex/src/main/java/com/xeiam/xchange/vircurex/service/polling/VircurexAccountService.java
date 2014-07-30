package com.xeiam.xchange.vircurex.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.PollingAccountService;
import com.xeiam.xchange.vircurex.VircurexAdapters;

public class VircurexAccountService extends VircurexAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public VircurexAccountService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return VircurexAdapters.adaptAccountInfo(getVircurexAccountInfo());
  }

  @Override
  public String requestDepositAddress(String currency, String... arguments) throws IOException {

    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String withdrawFunds(String currecny, BigDecimal amount, String address) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    throw new NotYetImplementedForExchangeException();

  }
}
