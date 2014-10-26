package com.xeiam.xchange.coinsetter.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.coinsetter.CoinsetterAdapters;
import com.xeiam.xchange.coinsetter.dto.account.CoinsetterAccount;
import com.xeiam.xchange.coinsetter.dto.clientsession.response.CoinsetterClientSession;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.PollingAccountService;

public class CoinsetterAccountService extends CoinsetterBasePollingService implements PollingAccountService {

  private final CoinsetterAccountServiceRaw accountServiceRaw;

  /**
   * @param exchangeSpecification
   */
  public CoinsetterAccountService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    accountServiceRaw = new CoinsetterAccountServiceRaw(exchangeSpecification);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AccountInfo getAccountInfo() throws IOException {

    CoinsetterClientSession session = getSession();
    CoinsetterAccount account = accountServiceRaw.get(session.getUuid(), getAccountUuid());
    return CoinsetterAdapters.adaptAccountInfo(session.getUsername(), account);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String requestDepositAddress(String currency, String... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

}
