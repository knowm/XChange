package com.xeiam.xchange.okcoin.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.okcoin.OkCoinAdapters;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

public class OkCoinFuturesAccountService extends OkCoinAccountServiceRaw implements PollingAccountService {

  /**
   * @param exchangeSpecification the exchange specification.
   */
  public OkCoinFuturesAccountService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AccountInfo getAccountInfo() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    return OkCoinAdapters.adaptAccountInfo(getFutureUserInfo());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    throw new NotAvailableFromExchangeException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String requestDepositAddress(String currency, String... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    throw new NotAvailableFromExchangeException();
  }

}
