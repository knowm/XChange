package org.knowm.xchange.examples.mercadobitcoin.account;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.mercadobitcoin.InteractiveAuthenticatedExchange;
import org.knowm.xchange.mercadobitcoin.dto.MercadoBitcoinBaseTradeApiResult;
import org.knowm.xchange.mercadobitcoin.dto.account.MercadoBitcoinAccountInfo;
import org.knowm.xchange.mercadobitcoin.service.MercadoBitcoinAccountServiceRaw;
import org.knowm.xchange.service.account.AccountService;

/**
 * Example showing the following:
 *
 * <ul>
 *   <li>Connect to Mercado Bitcoin exchange with authentication
 *   <li>View account balance
 *   <li>Get the bitcoin deposit address
 *   <li>Withdraw a small amount of BTC
 * </ul>
 *
 * @author Copied from Bitstamp and adapted by Felipe Micaroni Lalli
 */
public class MercadoBitcoinAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange mercadoBitcoin = InteractiveAuthenticatedExchange.createInstanceFromDefaultInput();
    AccountService accountService = mercadoBitcoin.getAccountService();

    generic(accountService);
    raw((MercadoBitcoinAccountServiceRaw) accountService);
  }

  private static void generic(AccountService accountService) throws IOException {

    // Get the account information
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("AccountInfo as String: " + accountInfo.toString());
  }

  private static void raw(MercadoBitcoinAccountServiceRaw accountService) throws IOException {

    // Get the account information
    MercadoBitcoinBaseTradeApiResult<MercadoBitcoinAccountInfo> mercadoBitcoinAccountInfo =
        accountService.getMercadoBitcoinAccountInfo();
    System.out.println(
        "MercadoBitcoinBaseTradeApiResult<MercadoBitcoinAccountInfo> as String: "
            + mercadoBitcoinAccountInfo.toString());
  }
}
