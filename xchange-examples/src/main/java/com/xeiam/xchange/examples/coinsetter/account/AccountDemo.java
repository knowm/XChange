package com.xeiam.xchange.examples.coinsetter.account;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinsetter.service.polling.CoinsetterAccountService;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.examples.coinsetter.CoinsetterExamplesUtils;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

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
