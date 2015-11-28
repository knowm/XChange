package com.xeiam.xchange.examples.mexbt.account;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.examples.mexbt.MeXBTDemoUtils;
import com.xeiam.xchange.mexbt.dto.MeXBTException;
import com.xeiam.xchange.mexbt.dto.account.MeXBTBalanceResponse;
import com.xeiam.xchange.mexbt.dto.account.MeXBTDepositAddressesResponse;
import com.xeiam.xchange.mexbt.dto.account.MeXBTUserInfoResponse;
import com.xeiam.xchange.mexbt.service.polling.MeXBTAccountServiceRaw;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

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
