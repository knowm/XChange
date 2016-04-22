package org.knowm.xchange.examples.coinsetter.clientsession;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinsetter.CoinsetterException;
import org.knowm.xchange.coinsetter.dto.CoinsetterResponse;
import org.knowm.xchange.coinsetter.dto.clientsession.response.CoinsetterClientSession;
import org.knowm.xchange.coinsetter.service.polling.CoinsetterClientSessionServiceRaw;
import org.knowm.xchange.examples.coinsetter.CoinsetterExamplesUtils;

public class CoinsetterClientSessionDemo {

  private static final Logger log = LoggerFactory.getLogger(CoinsetterClientSessionDemo.class);

  public static void main(String[] args) throws IOException, InterruptedException {

    String username = args[0];
    String password = args[1];
    String ipAddress = args[2];

    Exchange coinsetter = CoinsetterExamplesUtils.getExchange();
    CoinsetterClientSessionServiceRaw clientSessionService = new CoinsetterClientSessionServiceRaw(coinsetter);
    CoinsetterClientSession clientSession = clientSessionService.login(username, password, ipAddress);
    log.info("Client session: {}", clientSession);

    for (int i = 0; i < 2; i++) {
      TimeUnit.SECONDS.sleep(30);
      CoinsetterResponse heartbeatResponse = clientSessionService.heartbeat(clientSession.getUuid());
      log.info("hearbeat: {}", heartbeatResponse);
    }

    CoinsetterResponse logoutResponse = clientSessionService.logout(clientSession.getUuid());
    log.info("logout: {}", logoutResponse);

    try {
      CoinsetterResponse heartbeatResponse = clientSessionService.heartbeat(clientSession.getUuid());
      log.info("hearbeat: {}", heartbeatResponse);
    } catch (CoinsetterException e) {
      log.info("{}", e.getMessage());
    }
  }

}
