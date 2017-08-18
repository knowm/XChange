package org.knowm.xchange.examples.hitbtc.account;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.hitbtc.HitbtcExampleUtils;
import org.knowm.xchange.hitbtc.dto.account.HitbtcBalance;
import org.knowm.xchange.hitbtc.dto.general.HitbtcTransferType;
import org.knowm.xchange.hitbtc.service.HitbtcAccountServiceRaw;
import org.knowm.xchange.service.account.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.mazi.rescu.HttpStatusIOException;

public class HitbtcAccountDemo {

  private static final Logger LOGGER = LoggerFactory.getLogger(HitbtcAccountDemo.class.getCanonicalName());

  public static void main(String[] args) throws IOException {

    Exchange exchange = HitbtcExampleUtils.createSecureExchange();
    AccountService accountService = exchange.getAccountService();

    generic(accountService);
    raw((HitbtcAccountServiceRaw) accountService);
  }

  private static void generic(AccountService accountService) throws IOException {

    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println(accountInfo);

  }

  private static void raw(HitbtcAccountServiceRaw accountService) throws IOException {

    HitbtcBalance[] accountInfo = accountService.getWalletRaw();
    System.out.println(Arrays.toString(accountInfo));

    try {
      accountService.transferFunds(Currency.USD, new BigDecimal(1.00), HitbtcTransferType.BANK_TO_EXCHANGE);
    } catch (HttpStatusIOException e) {
      LOGGER.error("Error transferring funds: " + e.getMessage(), e);
    }

  }

}