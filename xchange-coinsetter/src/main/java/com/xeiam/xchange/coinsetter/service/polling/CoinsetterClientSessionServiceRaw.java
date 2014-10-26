package com.xeiam.xchange.coinsetter.service.polling;

import java.io.IOException;
import java.util.UUID;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinsetter.CoinsetterException;
import com.xeiam.xchange.coinsetter.dto.CoinsetterResponse;
import com.xeiam.xchange.coinsetter.dto.clientsession.request.CoinsetterLoginRequest;
import com.xeiam.xchange.coinsetter.dto.clientsession.response.CoinsetterClientSession;

/**
 * Client session raw service.
 */
public class CoinsetterClientSessionServiceRaw extends CoinsetterBasePollingService {

  protected com.xeiam.xchange.coinsetter.CoinsetterClientSession clientSession;

  /**
   * @param exchangeSpecification
   */
  public CoinsetterClientSessionServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    String baseUrl = exchangeSpecification.getSslUri();
    clientSession = RestProxyFactory.createProxy(com.xeiam.xchange.coinsetter.CoinsetterClientSession.class, baseUrl);
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
