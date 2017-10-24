package org.knowm.xchange.examples.campbx.account;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.campbx.CampBXExchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;

/**
 * Demonstrate requesting Market Data from CampBX
 */
public class CampBXAccountDataDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Campbx exchange API using default settings
    Exchange campbx = ExchangeFactory.INSTANCE.createExchange(CampBXExchange.class.getName());

    campbx.getExchangeSpecification().setUserName("XChange");
    campbx.getExchangeSpecification().setPassword("The Java API");

    AccountService accountService = campbx.getAccountService();

    AccountInfo wallet = accountService.getAccountInfo();
    System.out.println("wallet = " + wallet);

    String depositAddr = accountService.requestDepositAddress(Currency.BTC);
    System.out.println("depositAddr = " + depositAddr);

    String txid = accountService.withdrawFunds(Currency.BTC, new BigDecimal("0.1"), "XXX");
    System.out.println("See the withdrawal transaction: http://blockchain.info/tx-index/" + txid);
  }

}
