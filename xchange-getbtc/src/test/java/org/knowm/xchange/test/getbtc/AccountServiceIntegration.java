package org.knowm.xchange.test.getbtc;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.getbtc.GetbtcExchange;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.test.getbtc.GetbtcConfig;
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
    Exchange exchange = GetbtcConfig.getExchange();

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
