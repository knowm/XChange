package org.knowm.xchange.examples.bitstamp.account;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitstamp.BitstampAuthenticatedV2.Country;
import org.knowm.xchange.bitstamp.dto.account.BitstampBalance;
import org.knowm.xchange.bitstamp.dto.account.BitstampDepositAddress;
import org.knowm.xchange.bitstamp.service.BitstampAccountServiceRaw;
import org.knowm.xchange.examples.bitstamp.BitstampDemoUtils;
import org.knowm.xchange.service.account.AccountService;

public class BitstampSepaWithdrawal {

  public static void main(String[] args) throws IOException {

    Exchange bitstamp = BitstampDemoUtils.createExchange();
    AccountService accountService = bitstamp.getAccountService();

    raw((BitstampAccountServiceRaw) accountService);
  }

  private static void raw(BitstampAccountServiceRaw accountService) throws IOException {

    // Get the account information
    BitstampBalance bitstampBalance = accountService.getBitstampBalance();
    System.out.println("BitstampBalance: " + bitstampBalance);

    BitstampDepositAddress depositAddress = accountService.getBitstampBitcoinDepositAddress();
    System.out.println("BitstampDepositAddress address: " + depositAddress);

    accountService.withdrawSepa(
        new BigDecimal("150"),
        "Kovin Kostner",
        "BY13NBRB3600900000002Z00AB00",
        "DABAIE2D",
        "Minsk, Belarus, Main street 2",
        "197372",
        "Minsk",
        Country.Belarus.alpha2);
  }
}
