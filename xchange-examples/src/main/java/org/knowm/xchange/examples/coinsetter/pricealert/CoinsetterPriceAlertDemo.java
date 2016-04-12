package org.knowm.xchange.examples.coinsetter.pricealert;

import java.io.IOException;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinsetter.dto.clientsession.response.CoinsetterClientSession;
import org.knowm.xchange.coinsetter.dto.pricealert.request.CoinsetterPriceAlertRequest;
import org.knowm.xchange.coinsetter.dto.pricealert.response.CoinsetterPriceAlert;
import org.knowm.xchange.coinsetter.dto.pricealert.response.CoinsetterPriceAlertList;
import org.knowm.xchange.coinsetter.dto.pricealert.response.CoinsetterPriceAlertResponse;
import org.knowm.xchange.coinsetter.dto.pricealert.response.CoinsetterRemovePriceAlertResponse;
import org.knowm.xchange.coinsetter.service.polling.CoinsetterClientSessionServiceRaw;
import org.knowm.xchange.coinsetter.service.polling.CoinsetterPriceAlertServiceRaw;
import org.knowm.xchange.examples.coinsetter.CoinsetterExamplesUtils;
import org.knowm.xchange.examples.coinsetter.order.CoinsetterOrderDemo;

public class CoinsetterPriceAlertDemo {

  private static final Logger log = LoggerFactory.getLogger(CoinsetterOrderDemo.class);

  public static void main(String[] args) throws IOException {

    String username = args[0];
    String password = args[1];
    String ipAddress = args[2];

    Exchange coinsetter = CoinsetterExamplesUtils.getExchange();
    CoinsetterClientSessionServiceRaw clientSessionService = new CoinsetterClientSessionServiceRaw(coinsetter);
    CoinsetterPriceAlertServiceRaw priceAlertService = new CoinsetterPriceAlertServiceRaw(coinsetter);

    CoinsetterClientSession clientSession = clientSessionService.login(username, password, ipAddress);
    log.info("Client session: {}", clientSession);

    // Add
    CoinsetterPriceAlertRequest request = new CoinsetterPriceAlertRequest("EMAIL", "CROSSES", new BigDecimal("123.12"), "BTCUSD");
    CoinsetterPriceAlertResponse addResponse = priceAlertService.add(clientSession.getUuid(), request);
    log.info("add price alert response: {}", addResponse);

    // List
    CoinsetterPriceAlertList priceAlerts = priceAlertService.list(clientSession.getUuid());

    for (CoinsetterPriceAlert alert : priceAlerts.getPriceAlerts()) {
      log.info("{}", alert);
    }

    // Remove
    CoinsetterRemovePriceAlertResponse removeResponse = priceAlertService.remove(clientSession.getUuid(), addResponse.getUuid());
    log.info("remove price alert response: {}", removeResponse);

    clientSessionService.logout(clientSession.getUuid());
  }

}
