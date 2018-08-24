package org.knowm.xchange.test.exx;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exx.EXXExchange;
import org.knowm.xchange.service.account.AccountService;

/**
 * kevinobamatheus@gmail.com
 * @author kevingates
 *
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
    String apiKey = "9d8af6f8-4622-4a69-829d-be4b7dc4f5f1";
    String secretKey = "9d08eb163e4c46d26f08ac25c9c35c55a910ed37";
 
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(EXXExchange.class.getName());

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
