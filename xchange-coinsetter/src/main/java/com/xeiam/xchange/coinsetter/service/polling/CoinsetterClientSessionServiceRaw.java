package com.xeiam.xchange.coinsetter.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinsetter.CoinsetterException;
import com.xeiam.xchange.coinsetter.dto.clientsession.request.CoinsetterLoginRequest;
import com.xeiam.xchange.coinsetter.dto.clientsession.response.CoinsetterClientSession;
import com.xeiam.xchange.coinsetter.dto.clientsession.response.CoinsetterClientSessionResponse;

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

  public CoinsetterClientSessionResponse logout(CoinsetterClientSession session) throws CoinsetterException, IOException {

    return clientSession.action(session.getUuid(), "LOGOUT");
  }

  public CoinsetterClientSessionResponse heartbeat(CoinsetterClientSession session) throws CoinsetterException, IOException {

    return clientSession.action(session.getUuid(), "HEARTBEAT");
  }

}
