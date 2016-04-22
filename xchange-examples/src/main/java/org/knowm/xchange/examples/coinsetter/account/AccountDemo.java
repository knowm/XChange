package org.knowm.xchange.examples.coinsetter.account;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinsetter.service.polling.CoinsetterAccountService;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.coinsetter.CoinsetterExamplesUtils;
import org.knowm.xchange.service.polling.account.PollingAccountService;

/**
 * Generic account service demonstration.
 */
public class AccountDemo {

  private static final Logger log = LoggerFactory.getLogger(AccountDemo.class);

  public static void main(String[] args) throws IOException {

    String username = args[0];
    String password = args[1];
    String ipAddress = args[2];

    Exchange coinsetter = CoinsetterExamplesUtils.getExchange(username, password, ipAddress);
    PollingAccountService accountService = coinsetter.getPollingAccountService();

    AccountInfo accountInfo = accountService.getAccountInfo();
    log.info("account info: {}", accountInfo);

    ((CoinsetterAccountService) accountService).logout();
  }

}
