package org.knowm.xchange.examples.btctrade.account;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.btctrade.BTCTradeExchange;
import org.knowm.xchange.btctrade.dto.account.BTCTradeBalance;
import org.knowm.xchange.btctrade.dto.account.BTCTradeWallet;
import org.knowm.xchange.btctrade.service.BTCTradeAccountServiceRaw;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;

public class AccountInfoDemo {

  public static void main(String[] args) throws IOException {

    // API key with Read Only Permission or All Permissions.
    String publicKey = args[0];
    String privateKey = args[1];

    ExchangeSpecification spec = new ExchangeSpecification(BTCTradeExchange.class);
    spec.setApiKey(publicKey);
    spec.setSecretKey(privateKey);

    Exchange btcTrade = ExchangeFactory.INSTANCE.createExchange(spec);
    generic(btcTrade);
    raw(btcTrade);
  }

  private static void generic(Exchange exchange) throws IOException {

    AccountService accountService = exchange.getAccountService();
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("Account info: " + accountInfo);

    String depositAddress = accountService.requestDepositAddress(Currency.BTC);
    System.out.println("Deposit address: " + depositAddress);
  }

  private static void raw(Exchange exchange) throws IOException {

    BTCTradeAccountServiceRaw accountService =
        (BTCTradeAccountServiceRaw) exchange.getAccountService();
    BTCTradeBalance balance = accountService.getBTCTradeBalance();

    System.out.println("Balance result: " + balance.getResult());
    System.out.println("Balance message: " + balance.getMessage());
    System.out.println("Balance CNY balance: " + balance.getCnyBalance());
    System.out.println("Balance BTC balance: " + balance.getBtcBalance());
    System.out.println("Balance CNY reserved: " + balance.getCnyReserved());
    System.out.println("Balance BTC reserved: " + balance.getBtcReserved());

    BTCTradeWallet wallet = accountService.getBTCTradeWallet();
    System.out.println("Wallet address: " + wallet.getAddress());
  }
}
