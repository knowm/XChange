package org.knowm.xchange.examples.mexbt.account;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.examples.mexbt.MeXBTDemoUtils;
import org.knowm.xchange.mexbt.dto.MeXBTException;
import org.knowm.xchange.mexbt.dto.account.MeXBTBalanceResponse;
import org.knowm.xchange.mexbt.dto.account.MeXBTDepositAddressesResponse;
import org.knowm.xchange.mexbt.dto.account.MeXBTUserInfoResponse;
import org.knowm.xchange.mexbt.service.polling.MeXBTAccountServiceRaw;
import org.knowm.xchange.service.polling.account.PollingAccountService;

public class AccountDemo {

  public static void main(String[] args) throws MeXBTException, IOException {
    Exchange exchange = MeXBTDemoUtils.createExchange(args);
    PollingAccountService accountService = exchange.getPollingAccountService();
    MeXBTAccountServiceRaw rawAccountService = (MeXBTAccountServiceRaw) accountService;

    MeXBTUserInfoResponse userInfoResponse = rawAccountService.getMe();
    System.out.println(userInfoResponse);

    MeXBTBalanceResponse balanceResponse = rawAccountService.getBalance();
    System.out.println(balanceResponse);

    MeXBTDepositAddressesResponse depositAddressesResponse = rawAccountService.getDepositAddresses();
    System.out.println(depositAddressesResponse);

    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println(accountInfo);

    String address = accountService.requestDepositAddress(Currency.BTC);
    System.out.println(address);
  }

}
