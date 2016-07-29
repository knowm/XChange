package org.knowm.xchange.coinsetter.service.polling;

import static org.knowm.xchange.coinsetter.CoinsetterExchange.ACCOUNT_UUID_KEY;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinsetter.dto.clientsession.response.CoinsetterClientSession;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

/**
 * Polling service.
 */
public class CoinsetterBasePollingService extends BaseExchangeService implements BasePollingService {

  private final Logger log = LoggerFactory.getLogger(CoinsetterBasePollingService.class);
  private final CoinsetterClientSessionService clientSessionService;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinsetterBasePollingService(Exchange exchange) {

    super(exchange);

    clientSessionService = new CoinsetterClientSessionService(exchange);
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

    return (UUID) exchange.getExchangeSpecification().getExchangeSpecificParametersItem(ACCOUNT_UUID_KEY);
  }
}
