package com.xeiam.xchange.examples.poloniex.account;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.examples.poloniex.PoloniexExamplesUtils;
import com.xeiam.xchange.service.polling.PollingAccountService;

/**
 * @author Zach Holmes
 */

public class PoloniexAccountDemo {

  public static void main(String[] args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    Exchange poloniex = PoloniexExamplesUtils.getExchange();
    PollingAccountService accountService = poloniex.getPollingAccountService();

    System.out.println(accountService.getAccountInfo());
  }

}
