package com.xeiam.xchange.bittrex.v1.service.account;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.bittrex.v1.BittrexExchange;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.service.polling.PollingAccountService;

public class BittrexAccountTest {

  public static void main(String[] args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BittrexExchange.class.getName());
    ExchangeSpecification specification = exchange.getDefaultExchangeSpecification();
    specification.setApiKey("05bed3d5a268410581c366f22d45e5ac");
    specification.setSecretKey("806dcf45a352454fbb6912554150c699");
    exchange.applySpecification(specification);

    PollingAccountService accountService = exchange.getPollingAccountService();
    List<Wallet> wallets = accountService.getAccountInfo().getWallets();
    System.out.println(wallets);
  }
}
