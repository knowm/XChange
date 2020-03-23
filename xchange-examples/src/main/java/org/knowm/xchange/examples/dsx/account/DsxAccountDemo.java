package org.knowm.xchange.examples.dsx.account;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.dsx.DsxExampleUtils;
import org.knowm.xchange.dsx.v2.dto.DsxBalance;
import org.knowm.xchange.dsx.v2.service.DsxAccountServiceRaw;
import org.knowm.xchange.service.account.AccountService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DsxAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = DsxExampleUtils.createExchange();
    AccountService accountService = exchange.getAccountService();

    generic(accountService);
    raw((DsxAccountServiceRaw) accountService);
  }

  private static void generic(AccountService accountService) throws IOException {

    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println(accountInfo);
  }

  private static void raw(DsxAccountServiceRaw accountService) throws IOException {

    List<DsxBalance> accountInfo = accountService.getMainBalance();
    System.out.println(Arrays.toString(accountInfo.toArray()));
  }
}
