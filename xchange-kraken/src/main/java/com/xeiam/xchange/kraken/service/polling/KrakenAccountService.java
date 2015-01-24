package com.xeiam.xchange.kraken.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.kraken.KrakenAdapters;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

public class KrakenAccountService extends KrakenAccountServiceRaw implements PollingAccountService {

  public KrakenAccountService(ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> nonceFactory) {

    super(exchangeSpecification, nonceFactory);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return KrakenAdapters.adaptBalance(getKrakenBalance(), exchangeSpecification.getUserName());
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
