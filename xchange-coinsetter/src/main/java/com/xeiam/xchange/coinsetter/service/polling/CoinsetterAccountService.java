package com.xeiam.xchange.coinsetter.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinsetter.CoinsetterAdapters;
import com.xeiam.xchange.coinsetter.dto.account.CoinsetterAccount;
import com.xeiam.xchange.coinsetter.dto.clientsession.response.CoinsetterClientSession;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

/**
 * Account service.
 */
public class CoinsetterAccountService extends CoinsetterBasePollingService implements PollingAccountService {

  private final CoinsetterAccountServiceRaw accountServiceRaw;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinsetterAccountService(Exchange exchange) {

    super(exchange);
    accountServiceRaw = new CoinsetterAccountServiceRaw(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    CoinsetterClientSession session = getSession();
    CoinsetterAccount account = accountServiceRaw.get(session.getUuid(), getAccountUuid());
    return CoinsetterAdapters.adaptAccountInfo(session.getUsername(), account);
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
