package org.knowm.xchange.coinsetter.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinsetter.CoinsetterAdapters;
import org.knowm.xchange.coinsetter.dto.account.CoinsetterAccount;
import org.knowm.xchange.coinsetter.dto.clientsession.response.CoinsetterClientSession;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.polling.account.PollingAccountService;

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
    return new AccountInfo(session.getUsername(), CoinsetterAdapters.adaptWallet(account));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

}
