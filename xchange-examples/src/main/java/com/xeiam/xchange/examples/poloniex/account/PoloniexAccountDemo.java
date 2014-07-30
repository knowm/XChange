package com.xeiam.xchange.examples.poloniex.account;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.examples.poloniex.PoloniexExamplesUtils;
import com.xeiam.xchange.service.polling.PollingAccountService;
import com.xeiam.xchange.utils.CertHelper;

/**
 * @author Zach Holmes
 */

public class PoloniexAccountDemo {

  public static void main(String[] args) throws Exception {

    CertHelper.trustAllCerts();

    Exchange poloniex = PoloniexExamplesUtils.getExchange();
    PollingAccountService accountService = poloniex.getPollingAccountService();

    System.out.println(accountService.getAccountInfo());
  }

}
