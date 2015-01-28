package com.xeiam.xchange.examples.mercadobitcoin.account;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.examples.mercadobitcoin.InteractiveAuthenticatedExchange;
import com.xeiam.xchange.mercadobitcoin.dto.MercadoBitcoinBaseTradeApiResult;
import com.xeiam.xchange.mercadobitcoin.dto.account.MercadoBitcoinAccountInfo;
import com.xeiam.xchange.mercadobitcoin.service.polling.MercadoBitcoinAccountServiceRaw;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connect to Mercado Bitcoin exchange with authentication</li>
 * <li>View account balance</li>
 * <li>Get the bitcoin deposit address</li>
 * <li>Withdraw a small amount of BTC</li>
 * </ul>
 *
 * @author Copied from Bitstamp and adapted by Felipe Micaroni Lalli
 */
public class MercadoBitcoinAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange mercadoBitcoin = InteractiveAuthenticatedExchange.createInstanceFromDefaultInput();
    PollingAccountService accountService = mercadoBitcoin.getPollingAccountService();

    generic(accountService);
    raw((MercadoBitcoinAccountServiceRaw) accountService);
  }

  private static void generic(PollingAccountService accountService) throws IOException {

    // Get the account information
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("AccountInfo as String: " + accountInfo.toString());
  }

  private static void raw(MercadoBitcoinAccountServiceRaw accountService) throws IOException {

    // Get the account information
    MercadoBitcoinBaseTradeApiResult<MercadoBitcoinAccountInfo> mercadoBitcoinAccountInfo = accountService.getMercadoBitcoinAccountInfo();
    System.out.println("MercadoBitcoinBaseTradeApiResult<MercadoBitcoinAccountInfo> as String: " + mercadoBitcoinAccountInfo.toString());
  }
}
