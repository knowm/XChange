package com.xeiam.xchange.coinsetter.service.polling;

import static com.xeiam.xchange.coinsetter.CoinsetterExchange.ACCOUNT_UUID_KEY;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinsetter.dto.clientsession.response.CoinsetterClientSession;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

/**
 * Polling service.
 */
public abstract class CoinsetterBasePollingService extends BaseExchangeService implements BasePollingService {

  private final Logger log = LoggerFactory.getLogger(CoinsetterBasePollingService.class);
  private final Collection<CurrencyPair> symbols;
  private final CoinsetterClientSessionService clientSessionService;

  /**
   * @param exchangeSpecification
   */
  public CoinsetterBasePollingService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    symbols = Arrays.asList(CurrencyPair.BTC_USD);
    clientSessionService = new CoinsetterClientSessionService(exchangeSpecification);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<CurrencyPair> getExchangeSymbols() throws IOException {

    return symbols;
  }

  public CoinsetterClientSession getSession() throws IOException {
    return clientSessionService.getSession();
  }

  public void logout() throws IOException {
    log.debug("logging out...");
    clientSessionService.logout();
  }

  public UUID getAccountUuid() throws IOException {
    // call getSession to make sure the account UUID has been pulled in session logging in.
    getSession();

    return (UUID) exchangeSpecification.getExchangeSpecificParametersItem(ACCOUNT_UUID_KEY);
  }

}
