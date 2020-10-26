package org.knowm.xchange.test.exx;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exx.EXXExchange;
import org.knowm.xchange.service.account.AccountService;

/**
 * kevinobamatheus@gmail.com
 *
 * @author kevingates
 */
public class AccountServiceIntegration {

  public static void main(String[] args) {
    try {
      getAssetInfo();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void getAssetInfo() throws IOException {
    String apiKey = "";
    String secretKey = "";

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(EXXExchange.class);

    ExchangeSpecification exchangeSpecification = exchange.getDefaultExchangeSpecification();
    exchangeSpecification.setSslUri("https://trade.exx.com");
    exchangeSpecification.setApiKey(apiKey);
    exchangeSpecification.setSecretKey(secretKey);
    exchange.applySpecification(exchangeSpecification);

    AccountService accountService = exchange.getAccountService();

    try {
      System.out.println("accountInfo");
      System.out.println(accountService.getAccountInfo());
      System.out.println(accountService.getAccountInfo().getWallets());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
