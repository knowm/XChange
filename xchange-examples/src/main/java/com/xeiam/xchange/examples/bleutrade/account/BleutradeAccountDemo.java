package com.xeiam.xchange.examples.bleutrade.account;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.bleutrade.service.polling.BleutradeAccountServiceRaw;
import com.xeiam.xchange.examples.bleutrade.BleutradeDemoUtils;
import com.xeiam.xchange.service.polling.PollingAccountService;

public class BleutradeAccountDemo {

  public static void main(String[] args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException, InterruptedException {

    Exchange bleutrade = BleutradeDemoUtils.getExchange();
    PollingAccountService accountService = bleutrade.getPollingAccountService();

    // generic(accountService);
    raw((BleutradeAccountServiceRaw) accountService);
  }

  private static void generic(PollingAccountService accountService) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException,
      InterruptedException {

    System.out.println(accountService.requestDepositAddress("BTC"));
    Thread.sleep(1000);
    System.out.println(accountService.getAccountInfo());
    Thread.sleep(1000);
  }

  private static void raw(BleutradeAccountServiceRaw accountService) throws IOException {

    System.out.println(accountService.getBleutradeBalance("BTC"));
  }

}
