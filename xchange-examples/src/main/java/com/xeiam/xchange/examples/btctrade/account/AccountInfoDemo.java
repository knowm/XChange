/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.examples.btctrade.account;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btctrade.BTCTradeExchange;
import com.xeiam.xchange.btctrade.dto.account.BTCTradeBalance;
import com.xeiam.xchange.btctrade.dto.account.BTCTradeWallet;
import com.xeiam.xchange.btctrade.service.polling.BTCTradeAccountServiceRaw;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.PollingAccountService;

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
    PollingAccountService accountService = exchange.getPollingAccountService();
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("Account info: " + accountInfo);

    String depositAddress = accountService.requestDepositAddress(Currencies.BTC);
    System.out.println("Deposit address: " + depositAddress);
  }

  private static void raw(Exchange exchange) {
    BTCTradeAccountServiceRaw accountService =
        (BTCTradeAccountServiceRaw) exchange.getPollingAccountService();
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
