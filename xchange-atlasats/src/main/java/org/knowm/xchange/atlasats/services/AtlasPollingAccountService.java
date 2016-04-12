package org.knowm.xchange.atlasats.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.knowm.xchange.ExchangeException;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.NotAvailableFromExchangeException;
import org.knowm.xchange.NotYetImplementedForExchangeException;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.polling.BasePollingExchangeService;
import org.knowm.xchange.service.polling.PollingAccountService;

public class AtlasPollingAccountService extends BasePollingExchangeService implements PollingAccountService {

  public AtlasPollingAccountService(Exchange exchange) {

    super(exchange);

  }

  @Override
  public AccountInfo getAccountInfo() throws  IOException {

    return null;
  }

  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String requestDepositAddress(String currency, String... args) throws  IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Collection<CurrencyPair> getExchangeSymbols() throws IOException {

    Collection<CurrencyPair> currencyPairs = new ArrayList<CurrencyPair>();
    return currencyPairs;
  }

}
