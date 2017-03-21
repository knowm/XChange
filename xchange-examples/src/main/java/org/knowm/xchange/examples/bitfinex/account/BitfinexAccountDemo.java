package org.knowm.xchange.examples.bitfinex.account;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexMarginInfosResponse;
import org.knowm.xchange.bitfinex.v1.service.BitfinexAccountService;
import org.knowm.xchange.bitfinex.v1.service.BitfinexAccountServiceRaw;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.FundsInfo;
import org.knowm.xchange.examples.bitfinex.BitfinexDemoUtils;
import org.knowm.xchange.examples.util.AccountServiceTestUtil;
import org.knowm.xchange.service.account.AccountService;

import java.io.IOException;
import java.util.Date;

public class BitfinexAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange bfx = BitfinexDemoUtils.createExchange();
    AccountService accountService = bfx.getAccountService();

    marginInfo(accountService);
    fundsInfo(accountService);
  }

  private static void marginInfo(AccountService accountService) throws IOException {
    // Get the margin information
    BitfinexAccountServiceRaw accountServiceRaw = (BitfinexAccountServiceRaw) accountService;
    BitfinexMarginInfosResponse[] marginInfos = accountServiceRaw.getBitfinexMarginInfos();
    System.out.println("Margin infos response: " + marginInfos[0]);
  }

  private static void fundsInfo(AccountService accountService) throws IOException {
    // Get the funds information
    Date startDate = new Date(System.currentTimeMillis() - (1 * 12 * 30 * 24 * 60 * 60 * 1000L)); // approx 1 year history
    BitfinexAccountService.BitfinexFundsInfoHistoryParams histParams =
            new BitfinexAccountService.BitfinexFundsInfoHistoryParams(startDate, null, null, Currency.BTC);
    FundsInfo fundsInfo = accountService.getFundsInfo(histParams);
    AccountServiceTestUtil.printFundsInfo(fundsInfo);

    histParams = new BitfinexAccountService.BitfinexFundsInfoHistoryParams(startDate, null, null, Currency.USD);
    fundsInfo = accountService.getFundsInfo(histParams);
    AccountServiceTestUtil.printFundsInfo(fundsInfo);
  }

}
