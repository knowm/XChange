package org.knowm.xchange.examples.ccex.account;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ccex.dto.account.CCEXBalance;
import org.knowm.xchange.ccex.service.pooling.CCEXAccountServiceRaw;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.examples.ccex.CCEXExamplesUtils;
import org.knowm.xchange.service.polling.account.PollingAccountService;

public class CCEXAccountDemo {
	
	public static void main(String[] args) throws IOException, InterruptedException {

	    Exchange exchange = CCEXExamplesUtils.getExchange();

	    PollingAccountService accountService = exchange.getPollingAccountService();

	    generic(accountService);
	    TimeUnit.SECONDS.sleep(1);
	    raw((CCEXAccountServiceRaw) accountService);
	  }

	  private static void generic(PollingAccountService accountService) throws IOException, InterruptedException {

	    System.out.println("----------GENERIC---------");

	    Map<Currency, Balance> balances = accountService.getAccountInfo().getWallet().getBalances();
	    System.out.println(balances);
	    TimeUnit.SECONDS.sleep(1);
	    System.out.println(accountService.requestDepositAddress(Currency.BTC));

	  }

	  private static void raw(CCEXAccountServiceRaw accountService) throws IOException, InterruptedException {

	    System.out.println("------------RAW-----------");

	    List<CCEXBalance> wallets = accountService.getCCEXAccountInfo();
	    System.out.println(wallets);
	    TimeUnit.SECONDS.sleep(1);
	    System.out.println(accountService.getCCEXDepositAddress("BTC"));

	  }
}
