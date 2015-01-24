package com.xeiam.xchange.itbit.v1.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.itbit.v1.ItBitAdapters;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

public class ItBitAccountService extends ItBitAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public ItBitAccountService(ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> nonceFactory) {

    super(exchangeSpecification, nonceFactory);
  }

  @Override
  public AccountInfo getAccountInfo() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    return ItBitAdapters.adaptAccountInfo(getItBitAccountInfo());
  }

  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String requestDepositAddress(String currency, String... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    throw new NotYetImplementedForExchangeException();
  }
}
