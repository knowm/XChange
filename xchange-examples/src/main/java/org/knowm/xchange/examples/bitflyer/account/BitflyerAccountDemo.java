package org.knowm.xchange.examples.bitflyer.account;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitflyer.dto.account.BitflyerMarginAccount;
import org.knowm.xchange.bitflyer.dto.account.BitflyerMarginStatus;
import org.knowm.xchange.bitflyer.dto.account.BitflyerMarginTransaction;
import org.knowm.xchange.bitflyer.service.BitflyerAccountServiceRaw;
import org.knowm.xchange.examples.bitflyer.BitflyerDemoUtils;
import org.knowm.xchange.service.account.AccountService;

public class BitflyerAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = BitflyerDemoUtils.createExchange();
    AccountService accountService = exchange.getAccountService();

    marginInfo(accountService);
  }

  private static void marginInfo(AccountService accountService) throws IOException {
    // Get the margin information
    BitflyerAccountServiceRaw accountServiceRaw = (BitflyerAccountServiceRaw) accountService;

    List<BitflyerMarginAccount> marginAccounts = accountServiceRaw.getBitflyerMarginAccounts();
    System.out.println("Margin infos response: " + marginAccounts);

    BitflyerMarginStatus marginStatus = accountServiceRaw.getBitflyerMarginStatus();
    System.out.println(marginStatus);

    List<BitflyerMarginTransaction> marginHistory = accountServiceRaw.getBitflyerMarginHistory();
    System.out.println(marginHistory);
  }
}
