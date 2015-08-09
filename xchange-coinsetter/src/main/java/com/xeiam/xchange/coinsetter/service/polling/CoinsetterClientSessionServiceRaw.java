package com.xeiam.xchange.coinsetter.service.polling;

import java.io.IOException;
import java.util.UUID;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinsetter.CoinsetterException;
import com.xeiam.xchange.coinsetter.dto.CoinsetterResponse;
import com.xeiam.xchange.coinsetter.dto.clientsession.request.CoinsetterLoginRequest;
import com.xeiam.xchange.coinsetter.dto.clientsession.response.CoinsetterClientSession;
import com.xeiam.xchange.service.BaseExchangeService;

import si.mazi.rescu.RestProxyFactory;

/**
 * Client session raw service.
 */
public class CoinsetterClientSessionServiceRaw extends BaseExchangeService {

  protected com.xeiam.xchange.coinsetter.rs.CoinsetterClientSession clientSession;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinsetterClientSessionServiceRaw(Exchange exchange) {

    super(exchange);

    String baseUrl = exchange.getExchangeSpecification().getSslUri();
    clientSession = RestProxyFactory.createProxy(com.xeiam.xchange.coinsetter.rs.CoinsetterClientSession.class, baseUrl);
  }

  public CoinsetterClientSession login(String username, String password, String ipAddress) throws IOException {

    return clientSession.login(new CoinsetterLoginRequest(username, password, ipAddress));
  }

  public CoinsetterResponse logout(UUID clientSessionId) throws CoinsetterException, IOException {

    return clientSession.action(clientSessionId, "LOGOUT");
  }

  public CoinsetterResponse heartbeat(UUID clientSessionId) throws CoinsetterException, IOException {

    return clientSession.action(clientSessionId, "HEARTBEAT");
  }

}
