package com.xeiam.xchange.examples.coinsetter.newsalert;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinsetter.dto.clientsession.response.CoinsetterClientSession;
import com.xeiam.xchange.coinsetter.dto.newsalert.response.CoinsetterNewsAlert;
import com.xeiam.xchange.coinsetter.dto.newsalert.response.CoinsetterNewsAlertList;
import com.xeiam.xchange.coinsetter.service.polling.CoinsetterClientSessionServiceRaw;
import com.xeiam.xchange.coinsetter.service.polling.CoinsetterNewsAlertServiceRaw;
import com.xeiam.xchange.examples.coinsetter.CoinsetterExamplesUtils;

public class CoinsetterNewsAlertDemo {

  private static final Logger log = LoggerFactory.getLogger(CoinsetterNewsAlertDemo.class);

  public static void main(String[] args) throws IOException {

    String username = args[0];
    String password = args[1];
    String ipAddress = args[2];

    Exchange coinsetter = CoinsetterExamplesUtils.getExchange();
    CoinsetterClientSessionServiceRaw clientSessionService = new CoinsetterClientSessionServiceRaw(coinsetter);
    CoinsetterNewsAlertServiceRaw newsAlertService = new CoinsetterNewsAlertServiceRaw(coinsetter);

    CoinsetterClientSession clientSession = clientSessionService.login(username, password, ipAddress);
    log.info("Client session: {}", clientSession);

    CoinsetterNewsAlertList newsAlerts = newsAlertService.list(clientSession.getUuid());

    for (CoinsetterNewsAlert alert : newsAlerts.getMessageList()) {
      log.info("{}", alert);
    }

    clientSessionService.logout(clientSession.getUuid());
  }

}
