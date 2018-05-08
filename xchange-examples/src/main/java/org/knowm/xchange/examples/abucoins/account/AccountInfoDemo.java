package org.knowm.xchange.examples.abucoins.account;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.examples.abucoins.AbucoinsDemoUtils;
import org.knowm.xchange.service.account.AccountService;

/** Author: bryant_harris */
public class AccountInfoDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = AbucoinsDemoUtils.createExchange();
    AccountService accountService = exchange.getAccountService();

    // Get the account information
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("AccountInfo as String: " + accountInfo.toString());

    List<FundingRecord> funding =
        accountService.getFundingHistory(accountService.createFundingHistoryParams());
    System.out.println("Funding " + funding);
  }
}
