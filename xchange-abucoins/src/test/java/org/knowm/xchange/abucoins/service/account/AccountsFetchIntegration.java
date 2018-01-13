package org.knowm.xchange.abucoins.service.account;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.abucoins.AbucoinsExchange;
import org.knowm.xchange.abucoins.dto.account.AbucoinsAccount;
import org.knowm.xchange.abucoins.dto.account.AbucoinsPaymentMethod;
import org.knowm.xchange.abucoins.service.AbucoinsAccountService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;

/**
 * @author bryant_harris
 */
public class AccountsFetchIntegration {

  @Test
  public void accountsFetchTest() throws Exception {
    ExchangeSpecification exSpec = new AbucoinsExchange().getDefaultExchangeSpecification();
    // TODO Don't hardcode within code.
    //exSpec.setPassword("--Passphrase--");
    //exSpec.setApiKey("--Key--");
    //exSpec.setSecretKey("--Secret--");
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange( exSpec );
      
    AbucoinsAccountService accountService = (AbucoinsAccountService) exchange.getAccountService();
    AbucoinsAccount[] accountInfo = accountService.getAbucoinsAccounts();
    assertThat(accountInfo).isNotNull();
    System.out.println(Arrays.asList(accountInfo));
    
    AccountInfo info = accountService.getAccountInfo();
    System.out.println(info);
    
    AbucoinsPaymentMethod[] paymentMethods = accountService.getPaymentMethods();
    assertThat(paymentMethods).isNotNull();
    System.out.println(Arrays.asList(paymentMethods));
    
    String address = accountService.requestDepositAddress(Currency.BTC);
    assertThat(address).isNotNull();
    System.out.println("BTC: Address " + address);
    
    address = accountService.requestDepositAddress(Currency.ETH);
    assertThat(address).isNotNull();
    System.out.println("ETH: Address " + address);
    
    address = accountService.requestDepositAddress(Currency.BCH);
    assertThat(address).isNotNull();
    System.out.println("BCH: Address " + address);
  }

}
