package com.xeiam.xchange.examples.coinsetter.clientsession;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinsetter.CoinsetterException;
import com.xeiam.xchange.coinsetter.dto.clientsession.response.CoinsetterClientSession;
import com.xeiam.xchange.coinsetter.dto.clientsession.response.CoinsetterClientSessionResponse;
import com.xeiam.xchange.coinsetter.service.polling.CoinsetterClientSessionServiceRaw;
import com.xeiam.xchange.examples.coinsetter.CoinsetterExamplesUtils;

public class ClientSessionDemo {

  private static final Logger log = LoggerFactory.getLogger(ClientSessionDemo.class);

  public static void main(String[] args) throws IOException, InterruptedException {

    String username = args[0];
    String password = args[1];
    String ipAddress = args[2];

    Exchange coinsetter = CoinsetterExamplesUtils.getExchange();
    CoinsetterClientSessionServiceRaw clientSessionService = new CoinsetterClientSessionServiceRaw(coinsetter.getExchangeSpecification());
    CoinsetterClientSession clientSession = clientSessionService.login(username, password, ipAddress);
    log.info("Client session: {}", clientSession);

    for (int i = 0; i < 2; i++) {
      TimeUnit.SECONDS.sleep(30);
      CoinsetterClientSessionResponse heartbeatResponse = clientSessionService.heartbeat(clientSession);
      log.info("hearbeat: {}", heartbeatResponse);
    }

    CoinsetterClientSessionResponse logoutResponse = clientSessionService.logout(clientSession);
    log.info("logout: {}", logoutResponse);

    try {
      CoinsetterClientSessionResponse heartbeatResponse = clientSessionService.heartbeat(clientSession);
      log.info("hearbeat: {}", heartbeatResponse);
    } catch (CoinsetterException e) {
      log.info("{}", e.getMessage());
    }
  }

}
