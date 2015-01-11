package com.xeiam.xchange.examples.empoex.account;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.examples.empoex.EmpoExDemoUtils;
import com.xeiam.xchange.service.polling.PollingAccountService;

public class EmpoExAccountDemo {

  public static void main(String[] args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    Exchange empoex = EmpoExDemoUtils.getExchange();
    PollingAccountService accountService = empoex.getPollingAccountService();

    generic(accountService);
  }

  public static void generic(PollingAccountService accountService) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    System.out.println(accountService.getAccountInfo());
  }

}
