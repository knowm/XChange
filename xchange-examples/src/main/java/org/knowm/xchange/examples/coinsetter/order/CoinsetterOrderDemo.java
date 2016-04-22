package org.knowm.xchange.examples.coinsetter.order;

import java.io.IOException;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinsetter.dto.account.CoinsetterAccount;
import org.knowm.xchange.coinsetter.dto.account.CoinsetterAccountList;
import org.knowm.xchange.coinsetter.dto.clientsession.response.CoinsetterClientSession;
import org.knowm.xchange.coinsetter.dto.order.request.CoinsetterOrderRequest;
import org.knowm.xchange.coinsetter.dto.order.response.CoinsetterOrder;
import org.knowm.xchange.coinsetter.dto.order.response.CoinsetterOrderList;
import org.knowm.xchange.coinsetter.dto.order.response.CoinsetterOrderResponse;
import org.knowm.xchange.coinsetter.service.polling.CoinsetterAccountServiceRaw;
import org.knowm.xchange.coinsetter.service.polling.CoinsetterClientSessionServiceRaw;
import org.knowm.xchange.coinsetter.service.polling.CoinsetterOrderServiceRaw;
import org.knowm.xchange.examples.coinsetter.CoinsetterExamplesUtils;

public class CoinsetterOrderDemo {

  private static final Logger log = LoggerFactory.getLogger(CoinsetterOrderDemo.class);

  public static void main(String[] args) throws IOException {

    String username = args[0];
    String password = args[1];
    String ipAddress = args[2];

    Exchange coinsetter = CoinsetterExamplesUtils.getExchange();
    CoinsetterClientSessionServiceRaw clientSessionService = new CoinsetterClientSessionServiceRaw(coinsetter);
    CoinsetterAccountServiceRaw accountService = (CoinsetterAccountServiceRaw) coinsetter.getPollingAccountService();
    CoinsetterOrderServiceRaw orderService = new CoinsetterOrderServiceRaw(coinsetter);

    CoinsetterClientSession clientSession = clientSessionService.login(username, password, ipAddress);
    log.info("Client session: {}", clientSession);

    CoinsetterAccount tradeAccount = null;
    CoinsetterAccountList coinsetterAccounts = accountService.list(clientSession.getUuid());
    for (CoinsetterAccount account : coinsetterAccounts.getAccountList()) {
      log.info("account: {}", account.getAccountUuid());

      CoinsetterAccount a = accountService.get(clientSession.getUuid(), account.getAccountUuid());
      log.info("account: {}", a);

      tradeAccount = a;
    }

    // Add order
    log.info("Adding order...");
    CoinsetterOrderRequest request = new CoinsetterOrderRequest(clientSession.getCustomerUuid(), tradeAccount.getAccountUuid(), "BTCUSD", "BUY",
        "LIMIT", new BigDecimal("0.01"), 2, new BigDecimal("0.01"));
    CoinsetterOrderResponse orderResponse = orderService.add(clientSession.getUuid(), request);
    log.info("add order response: {}", orderResponse);

    // Get order
    log.info("Getting order...");
    CoinsetterOrder orderStatus = orderService.get(clientSession.getUuid(), orderResponse.getUuid());
    log.info("order {}: {}", orderStatus.getUuid(), orderStatus.getStage());

    // List order
    log.info("Listing order...");
    CoinsetterOrderList orderList = orderService.list(clientSession.getUuid(), tradeAccount.getAccountUuid(), "NEW-FILL");
    for (CoinsetterOrder order : orderList.getOrderList()) {
      log.info("order {}: {}", order.getUuid(), order.getStage());
    }

    // Cancel order
    log.info("Cancelling order...");
    orderResponse = orderService.cancel(clientSession.getUuid(), orderResponse.getUuid());
    log.info("cancel order response: {}", orderResponse);

    clientSessionService.logout(clientSession.getUuid());
  }

}
