package org.knowm.xchange.examples.coingi.account;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.coingi.CoingiDemoUtils;

import java.io.IOException;

public class CoingiBalancesDemo {
    public static void main(String[] args) throws IOException {
        Exchange coingi = CoingiDemoUtils.createExchange();

        AccountInfo accountInfo = coingi.getAccountService().getAccountInfo();
        System.out.printf("Wallet balances: %s\n", accountInfo.getWallet().getBalances());
    }
}