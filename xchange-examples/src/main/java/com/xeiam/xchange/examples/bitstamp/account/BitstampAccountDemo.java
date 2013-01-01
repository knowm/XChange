package com.xeiam.xchange.examples.bitstamp.account;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitstamp.BitstampExchange;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.account.polling.PollingAccountService;

/**
 * @author Matija Mazi <br/>
 * @created 1/1/13 6:53 PM
 */
public class BitstampAccountDemo {
  public static void main(String[] args) {

    ExchangeSpecification exSpec = new BitstampExchange().getDefaultExchangeSpecification();
    exSpec.setUserName(args[0]);
    exSpec.setPassword(args[1]);

    Exchange bitstamp = ExchangeFactory.INSTANCE.createExchange(exSpec);

    PollingAccountService accountService = bitstamp.getPollingAccountService();

    // Get the account information
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("AccountInfo as String: " + accountInfo.toString());

  }
}
